package com.cooksys.ftd.kata.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.cooksys.ftd.kata.ILepidopterologist;
import com.cooksys.ftd.kata.model.Sample;
import com.cooksys.ftd.kata.model.Species;

public class Lepidopterologist implements ILepidopterologist {

	@Override
	public boolean registerSpecies(Species species) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSpeciesRegistered(Species species) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Optional<Species> findSpeciesForSample(Sample sample) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean recordSample(Sample sample) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Sample> getSamplesForSpecies(Species species) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Species> getRegisteredSpecies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Species, List<Sample>> getTaxonomy() {
		// TODO Auto-generated method stub
		return null;
	}

}
