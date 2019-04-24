package com.galaticos.randomteam.service;

import org.springframework.stereotype.Service;

/**
 * Service to sort teams
 * 
 * @author <a href="mailto:willian.ribeiro@gmail.com">Willian Ribeiro</a>
 * 
 */

@Service
public interface RandomTeamService {
	
	/**
	 * 
	 */
	public void print();

	/**
	 * Method to sort teams.
	 * @param names
	 * @return
	 */
	public String sortTeams(String names);
}
