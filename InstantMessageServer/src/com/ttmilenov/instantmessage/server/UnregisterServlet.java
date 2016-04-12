package com.ttmilenov.instantmessage.server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ttmilenov.instantmessage.model.Contact;
import com.ttmilenov.instantmessage.model.EMFService;

@SuppressWarnings("serial")
public class UnregisterServlet extends HttpServlet {

	private static final Logger logger = Logger.getLogger(UnregisterServlet.class.getCanonicalName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Unregisters a device from the InstantMessageServer.");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter(Constants.SENDER_EMAIL);

		EntityManager em = EMFService.get().createEntityManager();
		try {
			Contact contact = Contact.find(email, em);
			if (contact != null) {
				em.remove(contact);
				logger.log(Level.WARNING, "Unregistered: " + contact.getId());
			}
		} finally {
			em.close();
		}
	}

}