package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public String viewEmployees(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "employees"; // Returns employees.html in the templates directory
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "create-employee"; // Returns create-employee.html
    }

    @PostMapping("/create")
    public String createEmployee(@ModelAttribute("employee") Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/employees"; // Redirects back to the employee list
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "edit-employee";
    }

    @PostMapping("/edit/{id}")
    public String updateEmployee(@PathVariable("id") Long id, @ModelAttribute("employee") Employee employee) {
        Employee existingEmployee = employeeService.getEmployeeById(id);

        existingEmployee.setName(employee.getName());
        existingEmployee.setRole(employee.getRole());

        employeeService.updateEmployee(id, existingEmployee);

        return "redirect:/employees";
    }

    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }
}
