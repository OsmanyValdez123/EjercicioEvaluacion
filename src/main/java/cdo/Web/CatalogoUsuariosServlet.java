package cdo.Web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cdo.Datos.Usuarios;
import cdo.Persistencia.GestorCatalogoUsuarios;

public class CatalogoUsuariosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   public CatalogoUsuariosServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		GeneraRespuestas(request, response , session);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	

	private void GeneraRespuestas(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String vista = request.getParameter("vista");
		try { 
			switch(vista) {
				case "editaUsuario":
					String id_usu = request.getParameter("id_usu");
					String nombre_usu = request.getParameter("nombre_usu");
					String email_usu = request.getParameter("email_usu");
					String genero_usu = request.getParameter("genero_usu");
					String estatus_usu = request.getParameter("estatus_usu"); 
					
					session.setAttribute("id_usu", id_usu);
					session.setAttribute("nombre_usu", nombre_usu);
					session.setAttribute("email_usu", email_usu);
					session.setAttribute("genero_usu", genero_usu);
					session.setAttribute("estatus_usu", estatus_usu);
					
					RedireccionarVista(request, response, "DetalleUsuario.jsp");
				break;	
				case "ActualizaUsuario":
					List<Usuarios> lstUsuActualizado = actualizaUsuario(request,response,session);
					int id = Integer.parseInt(request.getParameter("idUsuAct"));
					session.setAttribute("CAT_USU", lstUsuActualizado); 
					session.setAttribute("MensajeRespuesta", "Usuario Actualizado Correctamente: ID " + id);
					RedireccionarVista(request, response, "CatalogoUsuarios.jsp");
				break;	
				case "eliminaUsuario":
					List<Usuarios> lstUsuEliminado = eliminaUsuario(request,response,session);
					int idEliminado = Integer.parseInt(request.getParameter("id_usuElimina"));
					session.setAttribute("CAT_USU", lstUsuEliminado); 
					session.setAttribute("MensajeRespuesta", "Usuario Eliminado Correctamente: ID " + idEliminado);
					RedireccionarVista(request, response, "CatalogoUsuarios.jsp");
				break;
				case "agregaUsuario":
					List<Usuarios> lstUsuNuevo = agregaUsuario(request,response,session);
					session.setAttribute("CAT_USU", lstUsuNuevo); 
					session.setAttribute("MensajeRespuesta", "Usuario Agregado Correctamente. ");
					RedireccionarVista(request, response, "CatalogoUsuarios.jsp");
				break;	
				case "regresarCatalogo":
					session.setAttribute("MensajeRespuesta","");
					RedireccionarVista(request, response, "CatalogoUsuarios.jsp");
				break;
			}
		}catch(Exception e) {
			System.out.println("Error en el Menu de Opciones: " + e.toString()); 
		}
	}

	private List<Usuarios> agregaUsuario(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		GestorCatalogoUsuarios cat = new GestorCatalogoUsuarios();
		
		String email = request.getParameter("txtEmailNuevo");
		String genero = request.getParameter("txtGeneroNuevo");
		String nombre = request.getParameter("txtNombreNuevo");
		int estatus = Integer.parseInt(request.getParameter("slcEstatusNuevo"));
		List<Usuarios> lstUsu = new ArrayList<>();
		try {
			lstUsu = cat.agregaDatosUsuario(estatus,email,genero,nombre);
		}catch(Exception e) {
			System.out.println("Error en agregaUsuario. " + e.toString());
		} 
		return lstUsu;
	}

	private List<Usuarios> eliminaUsuario(HttpServletRequest request, HttpServletResponse response,	HttpSession session) {
		GestorCatalogoUsuarios cat = new GestorCatalogoUsuarios();
		int idUsuAct = Integer.parseInt(request.getParameter("id_usuElimina"));
		List<Usuarios> lstUsu = new ArrayList<>();
		try {
			lstUsu = cat.eliminaDatosUsuario(idUsuAct);
		}catch(Exception e) {
			System.out.println("Error en actualizaUsuario. " + e.toString());
		} 
		return lstUsu;
	}

	private List<Usuarios> actualizaUsuario(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		GestorCatalogoUsuarios cat = new GestorCatalogoUsuarios();
		int idUsuAct = Integer.parseInt(request.getParameter("idUsuAct"));
		String email = request.getParameter("txtEmailAct");
		String genero = request.getParameter("txtGeneroAct");
		String nombre = request.getParameter("txtNombreAct");
		int estatus = Integer.parseInt(request.getParameter("slcEstatusAct"));
		List<Usuarios> lstUsu = new ArrayList<>();
		try {
			lstUsu = cat.actualizaDatosUsuario(idUsuAct,estatus,email,genero,nombre);
		}catch(Exception e) {
			System.out.println("Error en actualizaUsuario. " + e.toString());
		} 
		return lstUsu;
		
	}

	private void enviarRespuestaJsonJS(HttpServletRequest request, HttpServletResponse response, String listaJson)
	{
		try
		{
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.write(listaJson);	
		}
		catch(Exception ex)
		{
			System.out.println("Error al re-direccionar vista." + ex.getMessage().toString());
		}
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
