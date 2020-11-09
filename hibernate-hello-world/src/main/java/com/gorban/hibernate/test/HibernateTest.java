package com.gorban.hibernate.test;

import com.gorban.hibernate.HibernateUtil;
import com.gorban.hibernate.dto.EmployeeEntity;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

class HibernateTest {

    @AfterAll
    static void afterAll() {
        HibernateUtil.shutdown();
    }

    @Test
    public void saveObjTest(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        //Add new Employee object
        EmployeeEntity emp = new EmployeeEntity();
        emp.setEmail("demo-user@mail.com");
        emp.setFirstName("demo");
        emp.setLastName("user");

        session.save(emp);

        session.getTransaction().commit();

    }

    @Test
    public void loadObjTest(){
        Session sessionOne = HibernateUtil.getSessionFactory().openSession();
        sessionOne.beginTransaction();

        // Create employee
        EmployeeEntity emp = new EmployeeEntity();
        emp.setFirstName("Dmitry");
        emp.setLastName("Gorban");
        emp.setEmail("d_gor@ukr.net");

        // Save Employee entity into First level cache;
        sessionOne.save(emp);

        // Get employee id for the future use
        Integer empID = emp.getEmployeeId();
        System.out.println("Employee Entity ID: " + empID);

        // Store Employee entity int the H2 In memory database;
        sessionOne.getTransaction().commit();

        /************************************************************/

        // Load Employee entity by class name and entity id
        System.out.println("***** Loading Employee entity by class name and entity id *****");

        Session sessionTwo = HibernateUtil.getSessionFactory().openSession();
        sessionTwo.getTransaction().begin();

        EmployeeEntity emp2 =  sessionTwo.load(EmployeeEntity.class, empID);
        System.out.println("Employee First Name: " + emp2.getFirstName());
        System.out.println("Employee Last Name: " + emp2.getLastName());
        System.out.println("Employee Email: " + emp2.getEmail());
        System.out.println("Entity name: " + sessionTwo.getEntityName(emp2));

        sessionTwo.getTransaction().commit();

        /**************************************************************/

        // Load Employee entity by entity name directly and entity ID
        System.out.println("***** Load Employee entity by entity name directly and entity ID *****");

        Session sessionThree = HibernateUtil.getSessionFactory().openSession();
        sessionThree.getTransaction().begin();

        EmployeeEntity emp3 = (EmployeeEntity) sessionThree.load("com.gorban.hibernate.dto.EmployeeEntity", empID);
        System.out.println("Employee First Name: " + emp3.getFirstName());
        System.out.println("Employee Last Name: " + emp3.getLastName());
        System.out.println("Employee Email: " + emp3.getEmail());

        sessionThree.getTransaction().commit();

        /*************************************************************/

        // Load Employee entity by Employee class object and ID
        System.out.println("***** Loading Employee entity by Employee class object and ID *****");

        Session sessionFour = HibernateUtil.getSessionFactory().openSession();
        sessionFour.beginTransaction();

        EmployeeEntity emp4 = new EmployeeEntity();
        sessionFour.load(emp4, empID);

        System.out.println("Employee First Name: " + emp4.getFirstName());
        System.out.println("Employee Last Name: " + emp4.getLastName());
        System.out.println("Employee Email: " + emp4.getEmail());
    }

}