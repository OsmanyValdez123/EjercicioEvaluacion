package cdo.Web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import cdo.Datos.Usuarios;
import cdo.Persistencia.GestorCatalogoUsuarios;

/**
 * Servlet implementation class ServidorUsuarioServlet
 */
public class ServidorUsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServidorUsuarioServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);	
		String vista = request.getParameter("vista");
		GeneraRespuesta(request,response,session,vista);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
	}
	
	private void GeneraRespuesta(HttpServletRequest request, HttpServletResponse response, HttpSession session,	String vista)
	{
		if(vista.equals("Inicio.jsp")) {
			String txtUsu = request.getParameter("txtUsu");
			String txtPass = request.getParameter("txtPass");
			if(txtUsu.equals("Javier") && txtPass.equals("123456")) {
				List<Usuarios> lstUsu = consultaUsuarios(request,response,session);
				session.setAttribute("CAT_USU", lstUsu); 
				session.setAttribute("MensajeRespuesta","");
				RedireccionarVista(request, response, "CatalogoUsuarios.jsp");
			}else {
				RedireccionarVista(request, response, "Inicio.jsp");
			}
		}else {
			RedireccionarVista(request, response, "Inicio.jsp");
		}
		
	}
	private List<Usuarios> consultaUsuarios(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		GestorCatalogoUsuarios cat = new GestorCatalogoUsuarios();
		List<Usuarios> lstUsu = new ArrayList<>();
		try {
			 lstUsu = cat.obtieneDatosUsuarios();
		}catch(Exception e) {
			System.out.println("Error al intentar ingresar al Gestor. "+ e.toString());
		}
		return lstUsu;
	}
	public void RedireccionarVista(HttpServletRequest request, HttpServletResponse response, String vista)
	{
		try
		{
			RequestDispatcher rdIndex = request.getRequestDispatcher("jsp/" + vista);			    	
		    rdIndex.forward(request, response);
		}
		catch(Exception ex)
		{
			System.out.println("Error al re-direccionar vista." + ex.getMessage().toString());
		}
	}

}
