package com.datamanager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.cloud.Timestamp;
import com.models.data.Lavorazione;
import com.models.data.Pianificazione;

public class PianificazioniDataManager {

	/* SINGLETON PATTERN */
	private static PianificazioniDataManager pianificazioniDataManager = null;
	
	private List<Pianificazione> listaPianificazioni;
	
	private PianificazioniDataManager() {
		listaPianificazioni = new ArrayList<Pianificazione>();
	}
	
	public static PianificazioniDataManager getPianificazioniDataManager() {
		if(pianificazioniDataManager == null)
			pianificazioniDataManager = new PianificazioniDataManager();
		return pianificazioniDataManager;
	}
	
	public Pianificazione getPianificazioneCorrente() {
		if(listaPianificazioni.isEmpty())
			return creaPianificazioneCorrente();
		Timestamp now = Timestamp.now();
		Pianificazione last = listaPianificazioni.get(listaPianificazioni.size()-1);
		Timestamp start = Timestamp.parseTimestamp(last.getTimeStampInizioPeriodo());
		Timestamp end = Timestamp.parseTimestamp(last.getTimeStampFinePeriodo());
		if(start.compareTo(now) < 0 && now.compareTo(end) < 0)
			return last;
		else 
			return creaPianificazioneCorrente();
	}
	
	@SuppressWarnings("deprecation")
	private Pianificazione creaPianificazioneCorrente() {
		Date d = Timestamp.now().toDate();
		d.setHours(d.getHours()+3);
		Pianificazione p = new Pianificazione(Timestamp.now().toString(), Timestamp.of(d).toString());
		listaPianificazioni.add(p);
		return p;
	}

	public Pianificazione getPianificazioneInLavorazione() {
		return listaPianificazioni.get(listaPianificazioni.size()-2);
	}
	
	public void addPianificazione(String timeStampInizioPeriodo, String timeStampFinePeriodo) {
		Pianificazione p = new Pianificazione(timeStampInizioPeriodo, timeStampFinePeriodo);
		listaPianificazioni.add(p);
	}

}
