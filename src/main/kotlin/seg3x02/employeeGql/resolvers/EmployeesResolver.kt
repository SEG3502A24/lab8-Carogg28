package seg3x02.employeeGql.resolvers

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeesRepository
import java.util.*

@Controller
class EmployeesResolver(private val employeeRepository: EmployeesRepository) {

    @QueryMapping
    fun employees(): List<Employee> = employeeRepository.findAll()

    @QueryMapping
    fun employeeById(@Argument id: String): Employee? = employeeRepository.findById(id).orElse(null)

    @MutationMapping
    fun newEmployee(@Argument createEmployeeInput: CreateEmployeeInput): Employee {
        val employee = Employee(
            name = createEmployeeInput.name,
            dateOfBirth = createEmployeeInput.dateOfBirth,
            city = createEmployeeInput.city,
            salary = createEmployeeInput.salary,
            gender = createEmployeeInput.gender,
            email = createEmployeeInput.email
        )
        employee.id = UUID.randomUUID().toString()  // Assign a unique ID
        return employeeRepository.save(employee)
    }
}

data class CreateEmployeeInput(
    val name: String,
    val dateOfBirth: String,
    val city: String,
    val salary: Float,
    val gender: String?,
    val email: String?
)
