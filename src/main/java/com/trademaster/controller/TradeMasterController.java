package com.trademaster.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.trademaster.core.OrdemStatus;
import com.trademaster.core.TradeMasterCore;
import com.trademaster.dao.BookDAO;
import com.trademaster.dao.CarteiraDAO;
import com.trademaster.dao.CotacaoDAO;
import com.trademaster.dao.InstrumentoDAO;
import com.trademaster.dao.OrdemDAO;
import com.trademaster.dao.UserDAO;
import com.trademaster.model.Book;
import com.trademaster.model.BookDetail;
import com.trademaster.model.Carteira;
import com.trademaster.model.Cotacao;
import com.trademaster.model.Instrumento;
import com.trademaster.model.InstrumentoCount;
import com.trademaster.model.JsonObject;
import com.trademaster.model.Ordem;
import com.trademaster.model.Trade;
import com.trademaster.model.Usuario;

@Controller
public class TradeMasterController {

	final static Logger logger = Logger.getLogger(TradeMasterController.class);

	private static String getUser() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String name = auth.getName(); // get logged in username
		return name;
	}

	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public ModelAndView index(ModelMap model) {

		logger.info("Accessing Home Page...");

		UserDAO userDAO = new UserDAO();
		Usuario savedUser = userDAO.findByUserName(getUser());

		ModelAndView mv = new ModelAndView();

		if (savedUser != null) {
			mv = new ModelAndView("trademaster_dashboard", "command",
					new Ordem());
			mv.addObject("username", savedUser.getUsername());
		} else {
			mv = new ModelAndView("trademaster_error");
			mv.addObject("error_desc", "Usuário " + getUser()
					+ " não cadastrado na base de dados!");
		}
		return mv;

	}

	@RequestMapping(value = "/admin", method = RequestMethod.POST)
	public String adm(ModelMap model) {

		UserDAO userDAO = new UserDAO();

		// find the saved user again.
		Usuario savedUser = userDAO.findByUserName(getUser());
		// System.out.println("2. find - savedUser : " + savedUser);

		model.addAttribute("user", savedUser);
		return "trademasteradm";

	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String indexfake(ModelMap model) {

		return "index";

	}

	@RequestMapping(value = "/instrumentomanutencao", method = RequestMethod.GET)
	public ModelAndView instMaintain(ModelMap model) {

		return new ModelAndView("instrumentomanutencao", "command",
				new Instrumento());

	}

	@RequestMapping(value = "/instrumentoincluir", method = RequestMethod.POST)
	public ModelAndView instAdd(@ModelAttribute("SpringWeb") Instrumento inst,
			ModelMap model) {

		InstrumentoDAO instDAO = new InstrumentoDAO();

		instDAO.save(inst);

		return new ModelAndView("instrumentomanutencao", "command",
				new Instrumento());

	}

	@RequestMapping(value = "/ordemincluir", method = RequestMethod.POST)
	public ModelAndView orderAdd(@ModelAttribute("SpringWeb") Ordem ordem,
			ModelMap model) {

		String err_desc = "";
		Boolean error = false;

		try {

			UserDAO userDAO = new UserDAO();
			Usuario user = userDAO.findByUserName(ordem.getUsuarioString());

			InstrumentoDAO instDAO = new InstrumentoDAO();
			Instrumento inst = instDAO.findBySymbol(ordem
					.getInstrumentoString());

			// Se uma ordem de venda, verifica se tem a quantidade suficiente na
			// carteira.
			CarteiraDAO cartDao = new CarteiraDAO();
			Carteira carteira = cartDao.findByInstrumentAndUser(inst, user);
			if (ordem.getTipo().equals("C")
					|| (!carteira.equals(null) && ordem.getQuantidade() <= carteira
							.getQuantidade())) {

				Calendar dataInclusao = Calendar.getInstance();

				Calendar dataVencimento = dataInclusao;
				dataVencimento.add(Calendar.DAY_OF_MONTH, 1);

				ordem.setUsuario(user);
				ordem.setInstrumento(inst);
				ordem.setDataInclusao(dataInclusao);
				ordem.setDataVencimento(dataVencimento);
				ordem.setDayTrade("N");
				ordem.setStatus(OrdemStatus.AGUARDANDO);
				ordem.setValorOperacao(ordem.getPreco() * ordem.getQuantidade());
				ordem.setVencimentoString(dataVencimento.getTime().toString());

				OrdemDAO ordemDAO = new OrdemDAO();

				ordemDAO.save(ordem);

				// executa trade
				TradeMasterCore trade = new TradeMasterCore();
				trade.executeTrade(ordem, user);
			} else {
				err_desc = "Quantidade insuficiente!";
				error = true;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ModelAndView mv = new ModelAndView("trademaster_dashboard", "command",
				new Ordem());
		mv.addObject("username", getUser());
		mv.addObject("error_desc", err_desc);
		mv.addObject("bError", error);

		return mv;

	}

	@RequestMapping(value = "/instrumentos", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	String instrumentos(HttpServletRequest request) throws IOException {

		InstrumentoDAO instDAO = new InstrumentoDAO();

		List<Instrumento> insts = instDAO.findAll();

		JsonObject jsonObj = new JsonObject();

		jsonObj.setData(insts);

		Gson json = new Gson();
		String output = json.toJson(jsonObj);

		return output;

	}

	@RequestMapping(value = "/instrumentosJSON", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	String instrumentosJSON(HttpServletRequest request) throws IOException {

		InstrumentoDAO instDAO = new InstrumentoDAO();

		List<Instrumento> insts = instDAO.findAll();
		Gson json = new Gson();
		String output = json.toJson(insts);

		return output;

	}

	@RequestMapping(value = "/carteira", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	String carteira(HttpServletRequest request) throws IOException {

		UserDAO userDAO = new UserDAO();
		// find the saved user again.
		Usuario savedUser = userDAO.findByUserName(getUser());

		CarteiraDAO cartDAO = new CarteiraDAO();

		List<Carteira> carts = cartDAO.findByUser(savedUser);

		JsonObject jsonObj = new JsonObject();

		jsonObj.setData(carts);
		// jsonObj.setDraw(1);
		// jsonObj.setRecordsTotal(carts.size());
		// jsonObj.setRecordsFiltered(carts.size());

		Gson json = new Gson();
		String output = json.toJson(jsonObj);

		return output;

	}

	@RequestMapping(value = "/cotacaoconsultar{equity}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	String cotacaoconsultar(HttpServletRequest request,
			@PathVariable("equity") String symbol) throws IOException {

		CotacaoDAO cotacaoDAO = new CotacaoDAO();

		Cotacao cotacao = cotacaoDAO.findBySymbol(symbol);
		Gson json = new Gson();
		String output = json.toJson(cotacao);

		return output;

	}
	
	@RequestMapping(value = "/cotacaoconsultar", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	String cotacaoconsultar(HttpServletRequest request) throws IOException {

		CotacaoDAO cotacaoDAO = new CotacaoDAO();

		List<Cotacao> cotacoes = cotacaoDAO.findAll();
		
		JsonObject jsonObj = new JsonObject();

		jsonObj.setData(cotacoes);
		
		Gson json = new Gson();
		String output = json.toJson(jsonObj);

		return output;

	}

	@RequestMapping(value = "/ordens", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	String ordens(HttpServletRequest request) throws IOException {

		UserDAO userDAO = new UserDAO();
		// find the saved user again.
		Usuario savedUser = userDAO.findByUserName(getUser());

		OrdemDAO cartDAO = new OrdemDAO();

		List<Ordem> ordens = cartDAO.findByUser(savedUser);

		JsonObject jsonObj = new JsonObject();

		jsonObj.setData(ordens);
		// jsonObj.setDraw(1);
		// jsonObj.setRecordsTotal(carts.size());
		// jsonObj.setRecordsFiltered(carts.size());

		Gson json = new Gson();
		String output = json.toJson(jsonObj);

		// System.out.println(output);

		return output;

	}

	@RequestMapping(value = "/abrirsistema", method = RequestMethod.GET)
	public String abrirSistema(HttpServletRequest request) throws IOException {

		InstrumentoDAO instDAO = new InstrumentoDAO();

		List<Instrumento> instrumentos = new ArrayList<Instrumento>();

		instrumentos = instDAO.findAll();

		CotacaoDAO cotacaoDAO = new CotacaoDAO();
		Cotacao cotacao = null;

		Calendar dataPregao = Calendar.getInstance();

		for (Instrumento instrumento : instrumentos) {
			cotacao = new Cotacao();
			cotacao.setDataPregao(dataPregao);
			cotacao.setHoraUltimoNegocio(null);
			cotacao.setInstrumento(instrumento);
			cotacao.setOscilacao(0d);
			cotacao.setPrecoAbertura(1d);
			cotacao.setPrecoFechamento(1d);
			cotacao.setPrecoMaximo(1d);
			cotacao.setPrecoMinimo(1d);
			cotacao.setQtdNegocios(0l);
			cotacao.setUltimoPreco(1d);
			cotacaoDAO.save(cotacao);
		}
		return "trademaster_dashboard";

	}

	@RequestMapping(value = "/cadastrausuarios", method = RequestMethod.GET)
	public String cadastrarUsuarios(HttpServletRequest request)
			throws IOException {

		UserDAO userDao = new UserDAO();
		Usuario user = new Usuario("ecaversan", "123456");
		userDao.save(user);

		user = new Usuario("tradeA", "123456");
		userDao.save(user);

		user = new Usuario("tradeB", "123456");
		userDao.save(user);

		user = new Usuario("admin", "123456");
		userDao.save(user);

		return "trademaster_dashboard";

	}

	@RequestMapping(value = "/geracarteiras", method = RequestMethod.GET)
	public ModelAndView gerarCarteiras(ModelMap model) throws IOException {

		UserDAO userDao = new UserDAO();
		InstrumentoDAO instDao = new InstrumentoDAO();
		CarteiraDAO cartDao = new CarteiraDAO();
		
		List<Usuario> usuarios = userDao.findAll();
		
		Usuario userA = usuarios.get(0);
		Usuario userB = usuarios.get(1);
		
		Ordem ordemA = new Ordem();
		Ordem ordemB = new Ordem();
		
		List<Instrumento> instrumentos = instDao.findAll();
		Carteira carteira = null;

		for (Usuario usuario : usuarios) {
			if (cartDao.findByUser(usuario).isEmpty()) {
				
				
				for (Instrumento instrumento : instrumentos) {
					
					Trade trade = new Trade();
					
					ordemA.setUsuario(userA);
					ordemB.setUsuario(usuario);
					if(userA.equals(usuario)){
						ordemA.setUsuario(userB);
					}

					trade.setAsk(ordemA);
					trade.setBid(ordemB);
					trade.setDataHora(Calendar.getInstance());
					trade.setPreco(1d);
					trade.setQuantidade(100l);
					List<Trade> trades = new ArrayList<Trade>();
					trades.add(trade);

					carteira = new Carteira();
					carteira.setInstrumento(instrumento);
					carteira.setUsuario(usuario);
					carteira.setQuantidade(100l);
					carteira.setTrades(trades);
					carteira.setPreco_atual(0d);
					cartDao.save(carteira);
				}
			}
		}

		ModelAndView mv = new ModelAndView("trademaster_dashboard", "command",
				new Ordem());
		mv.addObject("username", getUser());
		mv.addObject("error_desc", "Carteiras geradas com sucesso!");
		mv.addObject("bError", true);

		return mv;

	}

	@RequestMapping(value = "/instrumentosgroup", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	String instrumentosGroup(HttpServletRequest request) throws IOException {

		CarteiraDAO cartDao = new CarteiraDAO();

		UserDAO userDAO = new UserDAO();
		// find the saved user again.
		Usuario savedUser = userDAO.findByUserName(getUser());

		List<InstrumentoCount> insts = cartDao.findByUserGroupByInst(savedUser);

		Gson json = new Gson();
		String output = json.toJson(insts);

		return output;

	}
	
	@RequestMapping(value = "/book{equity}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	String bookconsultar(HttpServletRequest request,
			@PathVariable("equity") String symbol) throws IOException {

		BookDAO bookDao = new BookDAO();

		//Book book = bookDao.find(symbol);
		Book book = bookDao.findByInstrumentAgg(symbol);
		List<BookDetail> details = book.getDetail();
		
		JsonObject jsonObj = new JsonObject();
		jsonObj.setData(details);
		
		Gson json = new Gson();
		String output = json.toJson(jsonObj);

		return output;

	}
}
