package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Program2 {

    public static void main(String[] args) {

        var departmentDao = DaoFactory.createDepartmentDao();
        Scanner sc = new Scanner(System.in);

        System.out.println("====== Test 1: department findById =======");
        var department = departmentDao.findById(3);

        System.out.println(department);

        System.out.println("\n====== Test 2: department findAll =======");
        List<Department> listaTodosDepartment = departmentDao.findAll();

        for (var dep : listaTodosDepartment) {
            System.out.println(dep);
        }

        System.out.println("\n====== Test 3: department insert =======");
        var newDepartment = new Department(null, "Service Desk");
        departmentDao.insert(newDepartment);

        System.out.println("Inserted! New id = " + newDepartment.getId());

        System.out.println("\n====== Test 4: department update =======");
        department = departmentDao.findById(1);
        department.setName("Computers");
        departmentDao.update(department);

        System.out.println("Update completed!");

        System.out.println("\n====== Test 5: department delete =======");
        System.out.print("Enter an id for delete test: ");
        var id = sc.nextInt();

        departmentDao.deleteById(id);

        System.out.println("Delete completed!");

        sc.close();
    }
}