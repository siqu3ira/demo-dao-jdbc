package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;

public class Program {

    public static void main(String[] args) {

        Department dep = new Department(1, "Books");
        Seller sel = new Seller(1, "Bob", "bob@gmail.com", new Date(), 3000.0, dep);

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println(sel);
    }
}