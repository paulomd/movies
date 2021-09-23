package com.texoit.golden.raspberry.awards.vo;

import java.util.List;

public class IntervalWinMinMaxVO {
	
	private List<IntervalWinVO> min;
	private List<IntervalWinVO> max;
	
	public IntervalWinMinMaxVO() {
	}

	public IntervalWinMinMaxVO(List<IntervalWinVO> min, List<IntervalWinVO> max) {
		super();
		this.min = min;
		this.max = max;
	}

	public List<IntervalWinVO> getMin() {
		return min;
	}
	public void setMin(List<IntervalWinVO> min) {
		this.min = min;
	}
	public List<IntervalWinVO> getMax() {
		return max;
	}
	public void setMax(List<IntervalWinVO> max) {
		this.max = max;
	}
}
