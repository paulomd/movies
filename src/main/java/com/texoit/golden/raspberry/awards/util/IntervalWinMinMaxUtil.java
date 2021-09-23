package com.texoit.golden.raspberry.awards.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.texoit.golden.raspberry.awards.models.Movie;
import com.texoit.golden.raspberry.awards.vo.IntervalWinMinMaxVO;
import com.texoit.golden.raspberry.awards.vo.IntervalWinVO;

public class IntervalWinMinMaxUtil {
	
	public IntervalWinMinMaxVO getIntervalWinMinMax(List<Movie> movies) {
		movies = splitMoviesForProducer(movies);
		Map<String, List<Movie>> moviesProducers = movies.stream().collect(Collectors.groupingBy(Movie::getProducers));
		List<IntervalWinVO> intervalos = moviesProducers.entrySet().stream().map(this::toIntervalWinVO).collect(ArrayList::new, List::addAll, List::addAll);
		return createIntervalWinMinMaxVO(intervalos);
	}
	
	private List<Movie> splitMoviesForProducer(List<Movie> movies) {
		return movies.stream().map(this::splitMovieForProducer).collect(ArrayList::new, List::addAll, List::addAll);
	}
	
	private List<Movie> splitMovieForProducer(Movie movie) {
		List<String> producersList = movie.getProducersList();
		return producersList.stream().map(producer->{
			Movie clone = movie.clone();
			clone.setProducers(producer);
			return clone;
		}).collect(Collectors.toList());

	}

	private IntervalWinMinMaxVO createIntervalWinMinMaxVO(List<IntervalWinVO> intervalos) {
		IntervalWinMinMaxVO intervaloMinMaxVO = new IntervalWinMinMaxVO();
		if(intervalos.size() > 0) {
			intervalos.sort(Comparator.comparing(IntervalWinVO::getInterval));
			Integer intervalMin = intervalos.get(0).getInterval();
			Integer intervalMax = intervalos.get(intervalos.size()-1).getInterval();
			List<IntervalWinVO> minList = intervalos.stream().filter(intervalo->intervalo.getInterval() == intervalMin).collect(Collectors.toList());
			List<IntervalWinVO> maxList = intervalos.stream().filter(intervalo->intervalo.getInterval() == intervalMax).collect(Collectors.toList());
			intervaloMinMaxVO.setMin(minList);
			intervaloMinMaxVO.setMax(maxList);
		}
		return intervaloMinMaxVO;
	}

	private List<IntervalWinVO> toIntervalWinVO(Entry<String, List<Movie>> entry){
		List<IntervalWinVO> intervalList = new ArrayList<IntervalWinVO>();
		List<Movie> movies = entry.getValue();
		movies.sort(Comparator.comparing(Movie::getYear));
		if(movies.size() > 1) {
			for (int i = 0; i < movies.size()-1; i++) {
				IntervalWinVO intervalWinVO = new IntervalWinVO();
				intervalWinVO.setProducer(entry.getKey());
				intervalWinVO.setPreviousWin(movies.get(i).getYear());
				intervalWinVO.setFollowingWin(movies.get(i+1).getYear());
				intervalWinVO.setInterval(intervalWinVO.getFollowingWin()-intervalWinVO.getPreviousWin());
				intervalList.add(intervalWinVO);
			}
		}
		return intervalList;
	}
	
}
