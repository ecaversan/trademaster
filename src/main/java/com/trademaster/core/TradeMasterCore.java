package com.trademaster.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.data.domain.Sort;

import com.trademaster.dao.CarteiraDAO;
import com.trademaster.dao.CotacaoDAO;
import com.trademaster.dao.OrdemDAO;
import com.trademaster.dao.TradeDAO;
import com.trademaster.model.Carteira;
import com.trademaster.model.Cotacao;
import com.trademaster.model.Ordem;
import com.trademaster.model.Trade;
import com.trademaster.model.Usuario;

public class TradeMasterCore {

	public void executeTrade(Ordem ordem, Usuario user) {

		/** TODO **/
		// *********************************************************************************//
		// ******* IMPLEMENTAR A LÓGICA DE ATUALIZAÇÃO DE QUANTIDADE DAS OFERTAS
		// ***********//
		// *********************************************************************************//

		// Busca todas as ordens da equity correspondente que não são o usuário
		// em questão
		OrdemDAO ordemDAO = new OrdemDAO();
		List<Ordem> ordens = new ArrayList<Ordem>();
		List<Trade> trades = new ArrayList<Trade>();

		TradeDAO tradeDao = new TradeDAO();
		OrdemDAO ordemDao = new OrdemDAO();

		Long quantidade = ordem.getQuantidade();
		String C = "C";
		String V = "V";

		// Define a ordem dos preços
		Sort sort;
		if (ordem.getTipo().equals(C)) {

			sort = new Sort(Sort.Direction.DESC, "preco");
			ordens = ordemDAO.findByEquityAndNotUser(user,
					ordem.getInstrumentoString(), sort);
			for (Ordem ordemContraParte : ordens) {
				if (ordem.getPreco() >= ordemContraParte.getPreco()
						&& ordemContraParte.getTipo().equals(V)) {
					Trade trade = new Trade();
					trade.setAsk(ordem);
					trade.setBid(ordemContraParte);
					trade.setPreco(ordemContraParte.getPreco());
					trade.setQuantidade((quantidade < ordemContraParte
							.getQuantidade()) ? quantidade : ordemContraParte
							.getQuantidade());
					trade.setDataHora(Calendar.getInstance());

					tradeDao.save(trade);

					trades.add(trade);

					// Seta status da ordem de acordo com a quantidade executada
					if (quantidade <= ordemContraParte.getQuantidade()) {
						ordem.setStatus(OrdemStatus.EXECUTADA);
						ordemContraParte.setStatus(OrdemStatus.PARCIAL);
						if (quantidade == ordemContraParte.getQuantidade()) {
							ordemContraParte.setStatus(OrdemStatus.EXECUTADA);
						}
						ordemContraParte.setQuantidade(ordemContraParte
								.getQuantidade() - ordem.getQuantidade());
					} else {
						ordem.setStatus(OrdemStatus.PARCIAL);
						ordem.setQuantidade(quantidade
								- ordemContraParte.getQuantidade());
						ordemContraParte.setStatus(OrdemStatus.EXECUTADA);
					}

					ordemDao.save(ordemContraParte);
					ordemDao.save(ordem);

					quantidade = quantidade - trade.getQuantidade();

					if (quantidade == 0) {
						break;
					}
				}
			}
		} else {
			sort = new Sort(Sort.Direction.ASC, "preco");
			ordens = ordemDAO.findByEquityAndNotUser(user,
					ordem.getInstrumentoString(), sort);
			for (Ordem ordemContraParte : ordens) {
				if (ordem.getPreco() <= ordemContraParte.getPreco()
						&& ordemContraParte.getTipo().equals(C)) {
					Trade trade = new Trade();
					trade.setAsk(ordem);
					trade.setBid(ordemContraParte);
					trade.setPreco(ordemContraParte.getPreco());
					trade.setQuantidade((quantidade > ordemContraParte
							.getQuantidade()) ? quantidade : ordemContraParte
							.getQuantidade());
					trade.setDataHora(Calendar.getInstance());

					// Seta status da ordem de acordo com a quantidade executada
					if (quantidade <= ordemContraParte.getQuantidade()) {
						ordem.setStatus(OrdemStatus.EXECUTADA);
						ordemContraParte.setStatus(OrdemStatus.PARCIAL);
						if (quantidade == ordemContraParte.getQuantidade()) {
							ordemContraParte.setStatus(OrdemStatus.EXECUTADA);
						}
						ordemContraParte.setQuantidade(ordemContraParte
								.getQuantidade() - ordem.getQuantidade());
					} else {
						ordem.setStatus(OrdemStatus.PARCIAL);
						ordem.setQuantidade(quantidade
								- ordemContraParte.getQuantidade());
						ordemContraParte.setStatus(OrdemStatus.EXECUTADA);
					}

					ordemDao.save(ordemContraParte);
					ordemDao.save(ordem);

					tradeDao.save(trade);
					trades.add(trade);

					atualizaCotacao(trade);

					quantidade = quantidade - trade.getQuantidade();
					ordem.setQuantidade(quantidade);

					if (quantidade == 0) {
						break;
					}
				}
			}
		}

		// Atualiza carteira
		if (trades.size() > 0) {
			atualizaCarteiras(trades);
		}

	}

	public void atualizaCarteiras(List<Trade> trades) {

		for (Trade trade : trades) {
			atualizaCarteira(trade.getAsk(), trade);
			atualizaCarteira(trade.getBid(), trade);
		}

	}

	public void atualizaCarteira(Ordem ordem, Trade trade) {
		CarteiraDAO cartDao = new CarteiraDAO();
		List<Trade> trades = new ArrayList<Trade>();
		Carteira carteira = cartDao.findByInstrumentAndUser(
				ordem.getInstrumento(), ordem.getUsuario());
		if (carteira == null) {
			trades.add(trade);
			carteira = new Carteira();
			carteira.setInstrumento(ordem.getInstrumento());
			carteira.setPreco_atual(ordem.getPreco());
			carteira.setQuantidade(ordem.getQuantidade());
			carteira.setTrades(trades);
			carteira.setUsuario(ordem.getUsuario());
		} else {
			trades = carteira.getTrades();
			trades.add(trade);
			carteira.setTrades(trades);
			carteira.setPreco_atual(ordem.getPreco());
		}
		cartDao.save(carteira);

	}

	public void atualizaCotacao(Trade trade) {
		CotacaoDAO cotDao = new CotacaoDAO();
		Cotacao cot = cotDao
				.findBySymbol(trade.getAsk().getInstrumentoString());
		cot.setHoraUltimoNegocio(Calendar.getInstance());
		cot.setDataPregao(Calendar.getInstance());
		cot.setOscilacao(((trade.getPreco() * 100) / cot.getUltimoPreco()) - 100);
		cot.setUltimoPreco(trade.getPreco());
		if (cot.getPrecoMaximo() < trade.getPreco()) {
			cot.setPrecoMaximo(trade.getPreco());
		}
		if (cot.getPrecoMinimo() > trade.getPreco()) {
			cot.setPrecoMinimo(trade.getPreco());
		}
		cot.setQtdNegocios(cot.getQtdNegocios() + trade.getQuantidade());
		cotDao.save(cot);
	}
}
