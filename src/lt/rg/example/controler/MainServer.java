package lt.rg.example.controler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.LinkedHashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import lt.rg.example.model.DB_Queries;

@WebServlet({"/element", "/element/*"})
public class MainServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DB_Queries db = new DB_Queries();
	
    public MainServer() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
		System.out.println("is Init");
	}

	public void destroy() {
		System.out.println("is Destroy");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		
		PrintWriter pw = response.getWriter();
		
		if(request.getPathInfo() == null){
			System.out.println("is Get all element");
			
			JSONArray array = new JSONArray(db.getAllData());
			pw.append(array.toString());
		}else {
			String[] pathInfo = request.getPathInfo().substring(1).split("\\/"); 
			int id = 0;
			
			try {
				id = Integer.parseInt(pathInfo[0]);
				Element element = db.getDataById(id);

				if(element != null) {
					JSONObject object = new JSONObject(element);
					pw.append(object.toString());
				}else {
					response.setStatus(404);
					
					JSONObject object = new JSONObject();			
					object.put("status", "error");

					pw.append(object.toString());
				}
			} catch (Exception e) {
				response.setStatus(400);
				
				JSONObject object = new JSONObject();			
				object.put("status", "error");

				pw.append(object.toString());
			}

			System.out.println("is Get element id:"+id);
		}
	}

	public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		
		PrintWriter pw = response.getWriter();
		
		if(request.getPathInfo() == null){
			System.out.println("is Put error");
			
			response.setStatus(400);
			
			JSONObject object = new JSONObject();			
			object.put("status", "error");

			pw.append(object.toString());
		}else {
			String[] pathInfo = request.getPathInfo().substring(1).split("\\/"); 
			int id = 0;
			
			try {
				id = Integer.parseInt(pathInfo[0]);
				String name = "";
				int kiekis = 0;
				int spalva_id = 0;
				
				try {
					BufferedReader br =  request.getReader();
					
					String line = br.readLine();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<>();
					
					String[] brPart = line.split("&");
					
					for (String part : brPart) {
						String[] s = part.split("=");
						// URL Decoder
						map.put(s[0], URLDecoder.decode(s[1], "UTF-8"));
					}
					
					name = map.get("edit_name");
					kiekis = Integer.parseInt(map.get("edit_kiekis"));
					spalva_id = Integer.parseInt(map.get("edit_spalva_id"));
				} catch (Exception e) {
					response.setStatus(400);
					
					JSONObject object = new JSONObject();			
					object.put("status", "error");
					object.put("msg", "Bed varible type");

					pw.append(object.toString());
					
					System.out.println("is Put error");
					return;
				}
				
				Element element = db.setDataById(id, name, kiekis, spalva_id);

				if(element != null) {
					JSONObject object = new JSONObject(element);
					pw.append(object.toString());
				}else {
					response.setStatus(404);
					
					JSONObject object = new JSONObject();			
					object.put("status", "error");

					pw.append(object.toString());
				}
			} catch (Exception e) {
				response.setStatus(400);
				
				JSONObject object = new JSONObject();			
				object.put("status", "error");

				pw.append(object.toString());
			}

			System.out.println("is Put element id:"+id);
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		
		PrintWriter pw = response.getWriter();
		
		String name = "";
		int kiekis = 0;
		int spalva_id = 0;
		
		try {
			name = request.getParameter("add_name");
			kiekis = Integer.parseInt(request.getParameter("add_kiekis"));
			spalva_id = Integer.parseInt(request.getParameter("add_spalva_id"));
		} catch (Exception e) {
			response.setStatus(400);
			
			JSONObject object = new JSONObject();			
			object.put("status", "error");
			object.put("msg", "Bed varible type");
	
			pw.append(object.toString());
			
			System.out.println("is Post error");
			return;
		}
		
		Element element = db.addData(name, kiekis, spalva_id);

		if(element != null) {
			JSONObject object = new JSONObject(element);
			pw.append(object.toString());
		}else {
			response.setStatus(404);
			
			JSONObject object = new JSONObject();			
			object.put("status", "error");

			pw.append(object.toString());
		}
		
		System.out.println("is Post new element id:" + element.getId());
	}
	
	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		
		PrintWriter pw = response.getWriter();
		
		JSONObject object = new JSONObject();	
		
		if(request.getPathInfo() == null){
			System.out.println("is Delete error");
			
			response.setStatus(400);
			object.put("status", "error");
		}else {
			String[] pathInfo = request.getPathInfo().substring(1).split("\\/"); 
			int id = 0;
		
			try {
				id = Integer.parseInt(pathInfo[0]);
				// DB return
				if(db.removeDataById(id)) {
					object.put("status", "ok");
				}else {
					response.setStatus(404);
					object.put("status", "error");
				}
			} catch (Exception e) {
				response.setStatus(400);
				object.put("status", "error");	
			}
			
			System.out.println("is Delete element id:"+id);
		}
		
		pw.append(object.toString());
	}
}
