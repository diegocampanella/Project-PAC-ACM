package com.controllers;

import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.json.Json;
import com.google.api.client.json.JsonParser;
import com.models.data.Lavorazione;
import com.models.data.Lotto;
import com.services.ServicePianificazione;

@RestController
public class PianificazioneWebController {

	@Autowired
	private ServicePianificazione servicePianificazione;

	@PostMapping("/pianificazione/lotto/aggiungi")
	public void inserisciLotto(@RequestParam String idLotto, @RequestParam String idProdotto, @RequestParam int nPezzi,
			@RequestParam String priorita, @RequestParam String sequenzaLavorazioni) {
		StringTokenizer tokenizer = new StringTokenizer(sequenzaLavorazioni, ",");
		String[] token = new String[tokenizer.countTokens()];
		for (int i = 0; i < token.length; i++) {
			token[i] = tokenizer.nextToken();
		}
		servicePianificazione.inserisciLotto(idLotto, idProdotto, nPezzi, priorita, token);
	}

	@DeleteMapping("/pianificazione/lotto/elimina")
	public boolean cancellaLotto(@RequestParam String idLotto) {
		return servicePianificazione.cancellaLotto(idLotto);
	}

	@GetMapping("/pianificazione/lotto")
	public List<Lotto> visualizzaLottiPianificazioneCorrente() {
		return servicePianificazione.visualizzaLottiPianificazioneCorrente();
	}

	@PostMapping("/pianificazione/lotto")
	public boolean updateLotto(@RequestParam String idLotto, @RequestParam String idProdotto, @RequestParam int nPezzi,
			@RequestParam String priorita, @RequestParam String sequenzaLavorazioni) {
		StringTokenizer tokenizer = new StringTokenizer(sequenzaLavorazioni, ",");
		String[] token = new String[tokenizer.countTokens()];
		for (int i = 0; i < token.length; i++) {
			token[i] = tokenizer.nextToken();
		}
		servicePianificazione.inserisciLotto(idLotto, idProdotto, nPezzi, priorita, token);

		return servicePianificazione.updateLotto(idLotto, idProdotto, nPezzi, priorita, token);
	}

	@GetMapping("/pianificazione/idMacchina/{idMacchina}")
	public List<Lavorazione> getPianificazioneByMacchina(@PathVariable String idMacchina) {
		return servicePianificazione.visualizzaPianificazioneByMacchina(idMacchina);
	}

	@GetMapping("/pianificazione/calcolo")
	public List<Lavorazione> calcoloPianificazione(@RequestParam String listaMacchine,
			@RequestParam int slotSettimanali) {
		StringTokenizer tokenizer = new StringTokenizer(listaMacchine, ",");
		String[] token = new String[tokenizer.countTokens()];
		for (int i = 0; i < token.length; i++) {
			token[i] = tokenizer.nextToken();
		}
		return servicePianificazione.calcoloPianificazione(token, slotSettimanali);
	}

	@GetMapping("/pianificazione/residui")
	public List<Lotto> getLottiResiduiPianificazioneCorrente() {
		return servicePianificazione.getLottiResiduiPianificazioneCorrente();
	}

}
