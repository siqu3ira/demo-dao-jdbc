package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

public class Program {

    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("====== Test 1: seller findById =======");
        var seller = sellerDao.findById(3);

        System.out.println(seller);

        System.out.println("\n====== Test 2: seller findByDepartment =======");
        Department dep = new Department(2, null);
        List<Seller> listSeller = sellerDao.findByDepartment(dep);

        for (var sel : listSeller) {
            System.out.println(sel);
        }

        System.out.println("\n====== Test 3: seller findAll =======");
        List<Seller> listaTodosSellers = sellerDao.findAll();

        for (var sel : listaTodosSellers) {
            System.out.println(sel);
        }

        System.out.println("\n====== Test 4: seller findAll =======");
        var newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, dep);
        sellerDao.insert(newSeller);

        System.out.println("Inserted! New id = " + newSeller.getId());



        System.out.println("\n====== Test 5: seller update =======");
        seller = sellerDao.findById(1);
        seller.setName("Martha Waine");
        sellerDao.update(seller);

        System.out.println("Update completed!");


    }
}