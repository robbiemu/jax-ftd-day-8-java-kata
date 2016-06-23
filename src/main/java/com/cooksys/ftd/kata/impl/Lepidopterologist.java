package com.cooksys.ftd.kata.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.cooksys.ftd.kata.ILepidopterologist;
import com.cooksys.ftd.kata.model.GrowthModel;
import com.cooksys.ftd.kata.model.Sample;
import com.cooksys.ftd.kata.model.Species;
import com.rits.cloning.Cloner;

public class Lepidopterologist implements ILepidopterologist {

	private List<Species> speciesRegistry = new LinkedList<>();
	private Map<Species, Set<Sample>> samplesPerSpecies = new HashMap<>();

	private static final Cloner cloner = new Cloner();

	@Override
	public boolean registerSpecies(Species species) {
		if(species == null){
			return false;
		} else {
			speciesRegistry.add(species);
			return true;
		}
	}

	@Override
	public boolean isSpeciesRegistered(Species species) {
		return speciesRegistry.contains(species);
	}

	@Override
	public Optional<Species> findSpeciesForSample(Sample sample) {
		GrowthModel gm = GrowthModel.modelSample(sample);
		for(Species s: speciesRegistry){
			if(s.isMember(gm)){
				 return Optional.ofNullable(s);
			}
		}
		
		return Optional.ofNullable(null);
	}

	@Override
	public boolean recordSample(Sample sample) {
		if(sample == null) {
			return false;
		}
		
		boolean isMember = false;
		for(Species s: speciesRegistry){
			if(s.isMember(GrowthModel.modelSample(sample))){
				isMember = true;
				if(!samplesPerSpecies.containsKey(s)){
					Set<Sample> ss = new LinkedHashSet<>();
					ss.add(sample);
					
					samplesPerSpecies.put(s, ss);
				} else {
					samplesPerSpecies.get(s).add(sample);
				}
				break;
			}
		}
		
		return isMember;
	}

	@Override
	public List<Sample> getSamplesForSpecies(Species species) {
		List<Sample> rl;
		if(samplesPerSpecies.get(species) == null) {
System.out.println("returning empty list for species: " + species);
			rl = new ArrayList<>();
		} else {
			rl = new ArrayList<>( cloner.deepClone(samplesPerSpecies.get(species)) );			
		}
		Collections.sort(rl);
		
		return rl;
	}

	@Override
	public List<Species> getRegisteredSpecies() {
		List<Species> rl = cloner.deepClone(speciesRegistry);
		Collections.sort(rl);
		
		return rl;
	}

	@Override
	public Map<Species, List<Sample>> getTaxonomy() {
		Map<Species, List<Sample>> taxonomy = new HashMap<>();
		for(Species key: samplesPerSpecies.keySet()){
			List<Sample> ls = new ArrayList<>(cloner.deepClone(samplesPerSpecies.get(key)));
			Collections.sort(ls);

			taxonomy.put(key, ls);
		}
		
		return taxonomy;
	}

}
