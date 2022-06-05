package com.restapiexample.userinfo;

import com.restapiexample.constants.EndPoints;
import com.restapiexample.model.EmployeePojo;
import com.restapiexample.testbase.TestBase;
import com.restapiexample.utils.TestUtils;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;


//@RunWith(SerenityRunner.class)
public class EmployeeCURDTest extends TestBase {
    static String employee_name = "Hina" + TestUtils.getRandomValue();
    static String employee_salary = "65000";
    static String employee_age="35";
    static int employeeId;

    @Title("This will create a new employee")
    @Test
    public void test001() {
        EmployeePojo employeePojo =new EmployeePojo();
        employeePojo.setName(employee_name);
        employeePojo.setSalary(employee_salary);
        employeePojo.setAge(employee_age);

        SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(employeePojo)
                .when()
                .post(EndPoints.CREATE_EMPLOYEE)
                .then().log().all().statusCode(200);
    }

    @Title("Verify if the employee was added to the application")
    @Test
    public void test002() {
        employee_name="Brielle Williamson";
        String p1 = "data.findAll{it.employee_name=='";
        String p2 = "'}.get(0)";
        HashMap<String, Object> employeeMap = SerenityRest.given().log().all()
                .when()
                .get(EndPoints.GET_ALL_EMPLOYEES)
                .then()
                .statusCode(200)
                .extract()
                .path(p1 + employee_name + p2);
        Assert.assertThat(employeeMap, hasValue(employee_name));
        employeeId = (int) employeeMap.get("id");
        System.out.println(employeeId);
    }
    @Title("Update the employee information and verify the updated information for ID=5")
    @Test
    public void test003(){
        employee_name = "Hina22";
        employee_salary="80000";
        employee_age="24";
        employeeId=5;
        EmployeePojo employeePojo = new EmployeePojo();
        employeePojo.setName(employee_name);
        employeePojo.setSalary(employee_salary);
        employeePojo.setAge(employee_age);
        SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("employeeID", employeeId)
                .body(employeePojo)
                .when()
                .put(EndPoints.UPDATE_EMPLOYEE_BY_ID)
                .then().log().all().statusCode(200);

//        String p1 = "data.findAll{it.employee_name=='";
//        String p2 = "'}.get(0)";
//        HashMap<String, Object> userMap = SerenityRest.given().log().all()
//                .when()
//                .get(EndPoints.GET_ALL_EMPLOYEES)
//                .then()
//                .statusCode(200)
//                .extract()
//                .path(p1+employee_name+p2);
//        Assert.assertThat(userMap, hasValue(employee_name));
//        employeeId = (int) userMap.get("id");
//        System.out.println(employeeId);

    }

    @Title("Delete the employee and verify if the employee is deleted! for ID=2")
    @Test
    public void test004(){
        employeeId=2;
        SerenityRest.given().log().all()
                .pathParam("employeeID", employeeId)
                .when()
                .delete(EndPoints.DELETE_EMPLOYEE_BY_ID)
                .then().statusCode(200)
                .log().status();

//        SerenityRest.given().log().all()
//                .pathParam("employeeID", employeeId)
//                .when()
//                .get(EndPoints.GET_SINGLE_EMPLOYEE_BY_ID)
//                .then()
//                .statusCode(404);
    }

}
