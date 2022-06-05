package com.restapiexample.employeeinfo;


import com.restapiexample.constants.EndPoints;
import com.restapiexample.model.EmployeePojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class EmployeeSteps {
    @Step("Creating employee with employee_name : {0}, employee_salary: {1}, employee_age:{2}")
    public ValidatableResponse createEmployee(String employee_name, String employee_salary, String employee_age) {
        EmployeePojo employeePojo = new EmployeePojo();
        employeePojo.setName(employee_name);
        employeePojo.setSalary(employee_salary);
        employeePojo.setAge(employee_age);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(employeePojo)
                .when()
                .post(EndPoints.CREATE_EMPLOYEE)
                .then();
    }

    @Step("Getting the employee information with employee_name: {0}")
    public HashMap<String, Object> getEmployeeInfoByEmployeeName(String employee_name) {
        String p1 = "data.findAll{it.employee_name=='";
        String p2 = "'}.get(0)";
        return SerenityRest.given().log().all()
                .when()
                .get(EndPoints.GET_ALL_EMPLOYEES)
                .then()
                .statusCode(200)
                .extract()
                .path(p1 + employee_name + p2);
    }

    @Step("Updating employee information with employeeId: {0}, employee_name: {1}, employee_salary: {2},employee_age: {3}")
    public ValidatableResponse updateEmployee(int employeeId, String employee_name, String employee_salary, String employee_age) {
        EmployeePojo employeePojo = new EmployeePojo();
        employeePojo.setName(employee_name);
        employeePojo.setSalary(employee_salary);
        employeePojo.setAge(employee_age);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("employeeID", employeeId)
                .body(employeePojo)
                .when()
                .put(EndPoints.UPDATE_EMPLOYEE_BY_ID)
                .then();
    }

    @Step("Deleting the employee information with employeeId: {0}")
    public ValidatableResponse deleteEmployee(int employeeId) {
       return SerenityRest.given().log().all()
                .pathParam("employeeID", employeeId)
                .when()
                .delete(EndPoints.DELETE_EMPLOYEE_BY_ID)
                .then();
    }


}
