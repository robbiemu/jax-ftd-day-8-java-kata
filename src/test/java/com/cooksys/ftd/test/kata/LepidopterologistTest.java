package com.cooksys.ftd.test.kata;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.cooksys.ftd.kata.impl.Lepidopterologist;
import com.cooksys.ftd.kata.model.Butterpillar;
import com.cooksys.ftd.kata.model.Catterfly;
import com.cooksys.ftd.kata.model.GrowthModel;
import com.cooksys.ftd.kata.model.Sample;
import com.cooksys.ftd.kata.model.Species;

public class LepidopterologistTest {
	
	private Lepidopterologist Lepidopterologist;

	@Before
	public void before() {
		this.Lepidopterologist = new Lepidopterologist();
	}

	@Test
	public void testRegisterSpecies() {
		assertFalse("failed to return false on register null species",
				this.Lepidopterologist.registerSpecies(null) );

		Species s = new Species();
		assertTrue("failed to register species with registerSpecies",
				this.Lepidopterologist.registerSpecies(s) );
		
		Field f=null;
		try {
			f = this.Lepidopterologist.getClass().getDeclaredField("speciesRegistry");
		} catch(NoSuchFieldException e) {
			fail("Could not find speciesRegistry");
		}

		f.setAccessible(true);
		try {
			@SuppressWarnings("unchecked")					
			List<Species> ls = (List<Species>) f.get(this.Lepidopterologist);
			
			// how do I test object equality when equals is overridden?
			assertTrue("my species was not first registered", ls.get(0) == s);
		} catch (Exception e) {
			fail("speciesRegistry appears empty");
		}
	}

	@Test
	public void testIsSpeciesRegistered() {		
		Species s1 = new Species("some name", new GrowthModel(), 0D);
		Species s2 = new Species("some other name", new GrowthModel(), 0D);
		assertFalse("empty Lepidopterologist reports registered species", this.Lepidopterologist.isSpeciesRegistered(s1));

		this.Lepidopterologist.registerSpecies(s2);
		assertFalse("Lepidopterologist reported registered an unregistered species", this.Lepidopterologist.isSpeciesRegistered(s1));

		assertTrue("Lepidopterologist failed to report registered species", this.Lepidopterologist.isSpeciesRegistered(s2));
	}

	@Test
	public void testFindSpeciesForSample() {
		GrowthModel gm = new GrowthModel(1d,1d);
		Sample sa = new Sample(new Butterpillar(1d,1d), new Catterfly(1d,1d));
		Species sp = new Species("some name", gm, 0D);
		this.Lepidopterologist.registerSpecies(sp);
		
		Optional<Species> os = this.Lepidopterologist.findSpeciesForSample(sa);
		assertTrue("received a null species when should have been nonnull from findSpeciesForSample", os.isPresent());
		Species return_species = os.get();

		Sample sa_fail = new Sample(new Butterpillar(2d,1d), new Catterfly(5d,7d));
		os = this.Lepidopterologist.findSpeciesForSample(sa_fail);
		assertFalse("received a nonnull species when should have been null from findSpeciesForSample", os.isPresent());
		
		assertTrue("failed to find species for sample (returned null optional)", return_species != null);
		assertTrue("failed to find species for sample (species returned but was not the correct species!)", return_species == sp);
	}

	@Test
	public void testRecordSample() {
		assertFalse("failed to return false on record null sample",
				this.Lepidopterologist.recordSample(null) );

		GrowthModel gm = new GrowthModel(1d,1d);
		Sample sa = new Sample(new Butterpillar(1d,1d), new Catterfly(1d,1d));
		Species sp = new Species("some name", gm, 0.01);
		this.Lepidopterologist.registerSpecies(sp);
		assertTrue("failed to record sample at recordSample (Sample: " + sa + ")", 
				this.Lepidopterologist.recordSample(sa));
		
		Field f=null;
		try {
			f = this.Lepidopterologist.getClass().getDeclaredField("samplesPerSpecies");
		} catch(NoSuchFieldException e) {
			fail("Could not find samplesPerSpecies");
		}

		f.setAccessible(true);
		try {
			@SuppressWarnings("unchecked")					
			Map<Species, Set<Sample>> mss = (HashMap<Species, Set<Sample>>) f.get(this.Lepidopterologist);
			
			// how do I test object equality when equals is overridden?
			assertTrue("my species was not registered in the samples per species internal taxonomy", mss.containsKey(sp));
			assertTrue("my sample was not first registered for species (" + mss.get(sp).iterator().next() +" is not "+sa+")", 
					mss.get(sp).iterator().next() == sa);
		} catch (Exception e) {
			fail("samplesPerSpecies appears empty");
		}
	}

	@Test
	public void testGetSamplesForSpecies() {
		GrowthModel gm = new GrowthModel(1d,1d);
		Sample sa1 = new Sample(new Butterpillar(1d,1d), new Catterfly(1d,1d));
		Sample sa2 = new Sample(new Butterpillar(2d,2d), new Catterfly(2d,2d));
		Sample sa3 = new Sample(new Butterpillar(3d,3d), new Catterfly(3d,3d));
		Species sp = new Species("some name", gm, 0D);
		this.Lepidopterologist.registerSpecies(sp);
		this.Lepidopterologist.recordSample(sa2);
		this.Lepidopterologist.recordSample(sa1);
		this.Lepidopterologist.recordSample(sa3);

		List<Sample> lsa = this.Lepidopterologist.getSamplesForSpecies(sp);
		assertTrue("list of sample for species not correct (records: " + lsa.size() + "/3)", lsa.size()==3); 
		assertTrue("list sort order incorrect", 
				lsa.get(0).getButterpillar().getLeavesEaten() == 1d && 
				lsa.get(1).getButterpillar().getLeavesEaten() == 2d);
	}

	@Test
	public void testGetRegisteredSpecies() {
		String test_name = "some name";
		Species s1 = new Species(test_name, new GrowthModel(), 0D);
		Species s2 = new Species("some other name", new GrowthModel(), 0D);
		this.Lepidopterologist.registerSpecies(s2);
		this.Lepidopterologist.registerSpecies(s1);

		List<Species> lsp = this.Lepidopterologist.getRegisteredSpecies();
		assertTrue("list of species not proper length (wrong list returned?)", lsp.size()==2); 
		assertTrue("list sort order incorrect", lsp.get(0).getName() == test_name);		
	}

	@Test
	public void testGetTaxonomy() {
		Map<Species, List<Sample>> taxonomy = this.Lepidopterologist.getTaxonomy();
		assertTrue("null taxonomy", taxonomy != null);
		assertTrue("non-empty taxonomy", taxonomy.size()==0);

		GrowthModel rgm = new GrowthModel(1d,1d);
		Sample rsa1 = new Sample(new Butterpillar(1d,1d), new Catterfly(1d,1d));
		Sample rsa2 = new Sample(new Butterpillar(2d,2d), new Catterfly(2d,2d));
		Sample rsa3 = new Sample(new Butterpillar(3d,3d), new Catterfly(3d,3d));
		Species rsp = new Species("some weird name", rgm, 0D);
		this.Lepidopterologist.registerSpecies(rsp);
		this.Lepidopterologist.recordSample(rsa2);
		this.Lepidopterologist.recordSample(rsa1);
		this.Lepidopterologist.recordSample(rsa3);

		GrowthModel lgm = new GrowthModel(2d,1d);
		Sample lsa = new Sample(new Butterpillar(1d,1d), new Catterfly(2d,1d));
		Species lsp = new Species("some name", lgm, 0D);
		this.Lepidopterologist.registerSpecies(lsp);
		this.Lepidopterologist.recordSample(lsa);
		
		taxonomy = this.Lepidopterologist.getTaxonomy();
		assertTrue("invalid count of entries in taxonomy (size: " + taxonomy.size() + "/2)", taxonomy.size()==2);

		assertTrue("invalid samples for species in taxonomy", taxonomy.get(lsp).get(0).equals(lsa));
		
		assertTrue("invalid order of samples for species in taxonomy", taxonomy.get(rsp).get(0).equals(rsa1));
	}

}
