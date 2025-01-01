package userHandlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import DAO.UserDao;

public class DeleteUserHandler implements HttpHandler {
    private UserDao userDao;
    
    public DeleteUserHandler(UserDao userDao) {
        this.userDao = userDao;
    }

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub

	}

}
