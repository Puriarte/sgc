package com.puriarte.convocatoria.core.domain.services;

import java.util.Date;
import java.util.List;

import com.puriarte.convocatoria.persistence.PersonMovil;
import com.puriarte.convocatoria.persistence.SMS;
import com.puriarte.convocatoria.persistence.SmsStatus;

public class SmsService {

	public static SmsService getInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SMS> selectListByDispatch(Date fechaDesde, Date fechaHasta,
			Integer estado, Integer convocatoria, String order, Integer pos,
			Integer limit) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SMS> selectList(Date fechaDesde, Date fechaHasta,
			Integer estado, String order, Integer pos, Integer limit) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SMS> SelectRelatedSMSList(PersonMovil movil, SmsStatus status,
			int pos, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exist(String origen, Date date) {
		// TODO Auto-generated method stub
		return false;
	}

	public void insert(Object object, Object object2, Object object3,
			PersonMovil movil, String texto, int smsActionOutcome,
			SmsStatus selectSmsStatus, Date date) {
		// TODO Auto-generated method stub
		
	}

	public int selectCountSMS(int personId, String word, Date fechaDesde,
			Date fechaHasta) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int selectCountSentSMS(int personId, Date fechaDesde, Date fechaHasta) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int selectCountExpiredSMS(int personId, Date fechaDesde,
			Date fechaHasta) {
		// TODO Auto-generated method stub
		return 0;
	}

	public SmsStatus selectSmsStatus(int smsStatusRegistrationFailed) {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(SMS sms) {
		// TODO Auto-generated method stub
		
	}

	public List<SMS> selectList(int smsStatusPendiente) {
		// TODO Auto-generated method stub
		return null;
	}

}
