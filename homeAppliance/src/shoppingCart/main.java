package shoppingCart;

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
		tv.setId(1);
		
		ShoppingCartItem item1 = new ShoppingCartItem(1, aladin, tv);
		
		cart.addItem(item1);
		//add second item
		Appliance LCD = tvs.selectAppliance("lcd television");
		LCD.setId(2);
		
		ShoppingCartItem item2 = new ShoppingCartItem(1, aladin, LCD);
		cart.addItem(item2);

		
		System.out.println(item1.toString());
		System.out.println(item2.toString());
		
		
		//total up shopping cart
		System.out.println(cart.getTotalPrice());
		
	}

}
