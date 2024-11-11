package seg3x02.employeeGql.resolvers

import org.springframework.stereotype.Controller
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.Argument
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeesRepository
import java.util.*

@Controller
class EmployeesResolver(private val employeesRepository: EmployeesRepository) {

    @QueryMapping
    fun employees(): List<Employee> = employeesRepository.findAll()

    @QueryMapping
    fun employeeById(@Argument id: String): Employee? = employeesRepository.findById(id).orElse(null)

    @MutationMapping
    fun addEmployee(@Argument input: EmployeeInput): Employee {
        val newEmployee = Employee(
            name = input.name,
            dateOfBirth = input.dateOfBirth,
            city = input.city,
            salary = input.salary,
            gender = input.gender,
            email = input.email
        )
        newEmployee.id = UUID.randomUUID().toString()  // Assign a unique ID
        return employeesRepository.save(newEmployee)
    }

}

data class EmployeeInput(
    val name: String,
    val dateOfBirth: String,
    val city: String,
    val salary: Float,
    val gender: String?,
    val email: String?
)
