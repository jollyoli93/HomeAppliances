package shoppingCart;

import java.util.ArrayList;

import DAO.ShoppingDao;
import appliances.Appliance;
import appliances.EntertainmentFactory;
import users.BusinessUser;
import users.User;

public class main {

	public static void main(String[] args) {
		User aladin = new BusinessUser("Aladin", "Ababwa", "princeali@magiccarpet.com", "princeali", "jasmine123", "07438273647", "Alibaba");
		aladin.setCustomerId(5);
		
		ShoppingCart cart = new ShoppingCart(aladin);
		
		EntertainmentFactory tvs = new EntertainmentFactory();
		
		//add item to trolly
		Appliance tv = tvs.selectAppliance("basic television");
		tv.setId(8);
		
		ShoppingCartItem item1 = new ShoppingCartItem(aladin, tv);

//		//add second item
//		Appliance LCD = tvs.selectAppliance("lcd television");
//		LCD.setId(2);
//		
//		ShoppingCartItem item2 = new ShoppingCartItem(aladin, LCD);
//		item2.setId(2);

		ShoppingDao cartDao = new ShoppingDao("HomeAppliances");
		
		//cartDao.addNewLineItem(item1, null);
		
		ArrayList<ShoppingCartItem> items = new ArrayList<>();
		
		items = cartDao.findAll(5);
		
		for (ShoppingCartItem item : items ) {
			cart.addItem(item); 
		}
		System.out.println(cart.toString());
		
		cartDao.removeLineItem(8);
		
		for (ShoppingCartItem item : items ) {
			cart.addItem(item); 
		}
		System.out.println(cart.toString());
		
	}

}
