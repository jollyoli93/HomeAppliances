package DAO;

import java.util.ArrayList;
import java.util.Map;

import shoppingCart.ShoppingCart;

public class ShoppingDao extends DAO<ShoppingCart> {

	String cart_table = "CREATE TABLE \"shopping_cart\" (\n"
			+ "	\"cart_id\"	INTEGER NOT NULL,\n"
			+ "	\"user_id\"	INTEGER NOT NULL,\n"
			+ "	\"item_id\"	INTEGER NOT NULL,\n"
			+ "	\"price\"	INTEGER NOT NULL,\n"
			+ "	\"description\"	TEXT,\n"
			+ "	PRIMARY KEY(\"cart_id\"),\n"
			+ "	FOREIGN KEY(\"item_id\") REFERENCES \"appliances\"(\"id\") ON DELETE CASCADE,\n"
			+ "	FOREIGN KEY(\"user_id\") REFERENCES \"users\"(\"user_id\") ON DELETE CASCADE\n"
			+ ")";

	@Override
	public ArrayList<ShoppingCart> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateById(int id, String table, Map<String, Object> updateFields) {
		// TODO Auto-generated method stub
		return 0;
	}
}
