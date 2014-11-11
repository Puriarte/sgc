package com.puriarte.convocatoria.core.domain.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.puriarte.convocatoria.persistence.Dispatch;
import com.puriarte.convocatoria.persistence.Place;
import com.puriarte.convocatoria.persistence.SmsStatus;

public class DispatchsService {

	public static DispatchsService getInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	public int insert(Dispatch dispatch) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Dispatch> selectList(int estado, String order, Integer pos,
			Integer limit) {
		// TODO Auto-generated method stub
		return null;
	}

	public int insert(String message, String name, Place place,
			Date creationDate, Date scheduledDate, String[] personIds,
			HashMap categories, SmsStatus status) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Dispatch selectDispatch(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(Dispatch dispatch) {
		// TODO Auto-generated method stub
		
	}

	public void delete(Dispatch item1) {
		// TODO Auto-generated method stub
		
	}




}
