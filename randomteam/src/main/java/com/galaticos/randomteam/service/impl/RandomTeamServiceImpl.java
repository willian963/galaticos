package com.galaticos.randomteam.service.impl;

import org.springframework.stereotype.Service;

import com.galaticos.randomteam.entity.Player;
import com.galaticos.randomteam.service.RandomTeamService;

/**
 * ServiceImpl to sort teams
 * 
 * @author <a href="mailto:willian.ribeiro@gmail.com">Willian Ribeiro</a>
 * 
 */

@Service("randomTeamService")
public class RandomTeamServiceImpl implements RandomTeamService {

	@Override
	public void print() {
		System.out.println("Service running...");
	}

	@Override
	public String sortTeams(String names) {
		TeamSort teamSort = new TeamSort();
		String result = "";
		if (null != names && names.length() > 0) {
			String lines[] = names.split("\\r?\\n");
			for (String line : lines) {
				try {
					// tratando parametros
					if (line.contains("=")) {
						teamSort.readParams(line);
						continue;
					}

					Player p = TeamSort.createPlayer(line, teamSort.getShowPosition());

					if (p == null) {
						// System.out.println("Linha ignorada: " + line);
						continue;
					}

					teamSort.addPlayer(p);
				} catch (Exception e) {
					String message = "Erro ao ler arquivo de jogadores, linha: " + line;
					return message;
				}

			}
			if (teamSort.getQtdPlayer() != teamSort.getQtdFilePlayers()) {
				String message = "Existem " + teamSort.getQtdPlayer() + " jogadores relacionados. Devem ser relacionados "
						+ teamSort.getQtdFilePlayers() + " jogadores para a partida.";
				return message;
			}

			//validateIfNeddToReSortDesc(teamSort, 0f);
			//validateIfNeddToReSortAsc(teamSort, 0f);

			//validateIfNeddToReSortAsc(teamSort, 0f);
			validateIfNeddToReSortDesc(teamSort, 0f, 0);

			result= result.concat(("Times = " + teamSort.getQtdTeams() +"\n"));
			result = result.concat(("Jogadores = " + teamSort.getQtdPlayer()+"\n"));
			result = result.concat(("Soma Total das Notas = " + (teamSort.getTotalGrade()+"\n")));
			// System.out.println();
			result = result.concat((teamSort.getTeam(1)+"\n"));
			result = result.concat((teamSort.getTeam(2)+"\n"));
			// System.out.println(teamSort.getTeam(3));
		}else{
			String message = "Existem " + teamSort.getQtdPlayer() + " jogadores relacionados. Devem ser relacionados "
					+ teamSort.getQtdFilePlayers() + " jogadores para a partida.";
			return message;
		}
		return result;
	}

	/**
	 * Validate if need to re-sort asc
	 * @param teamSort
	 * @param gradeVariation
	 */
	private static void validateIfNeddToReSortAsc(TeamSort teamSort, float gradeVariation) {
		float teamsDiffGrade;
		int sortQtd = 0;

		teamSort.sortTeamAsc();
		teamsDiffGrade = teamSort.getTeamTotalGrade(1) - teamSort.getTeamTotalGrade(2);
		teamsDiffGrade = Math.abs(teamsDiffGrade);
		if (teamsDiffGrade > gradeVariation) {
			gradeVariation += 0.01;
			teamSort.sortTeamAsc();
			validateIfNeddToReSortAsc(teamSort, gradeVariation);
		}
	}

	/**
	 * Validate if need to re-sort desc
	 * @param teamSort
	 * @param gradeVariation
	 */
	private static void validateIfNeddToReSortDesc(TeamSort teamSort, float gradeVariation, int sortQtd) {
		float teamsDiffGrade;

		teamSort.sortTeamDesc();
		teamsDiffGrade = teamSort.getTeamTotalGrade(1) - teamSort.getTeamTotalGrade(2);
		teamsDiffGrade = Math.abs(teamsDiffGrade);
		if (teamsDiffGrade > gradeVariation) {
			if(sortQtd > 20){
				gradeVariation += 0.1;
				sortQtd = 0;
			}
			sortQtd++;
			teamSort.sortTeamDesc();
			validateIfNeddToReSortDesc(teamSort, gradeVariation, sortQtd);
		}
	}

}
