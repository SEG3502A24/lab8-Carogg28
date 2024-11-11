package seg3x02.employeeGql.resolvers

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeesRepository
import java.util.*

@Controller
class EmployeesResolver(private val employeesRepository: EmployeesRepository) {

    @QueryMapping
    fun employees(): List<Employee> = employeesRepository.findAll()

    @QueryMapping
    fun employeeById(@Argument id: String): Employee? =
        employeesRepository.findById(id).orElse(null)

    @QueryMapping
    fun employeeByEmail(@Argument email: String): Employee? =
        employeesRepository.findAll().find { it.email == email }

    @MutationMapping
    fun addEmployee(@Argument input: EmployeeInput): Employee {
        val newEmployee = Employee(
            name = input.name ?: throw Exception("Please enter a name"),
            dateOfBirth = input.dateOfBirth ?: throw Exception("Please enter a valid date of birth"),
            city = input.city ?: throw Exception("Please enter a valid city"),
            salary = input.salary ?: throw Exception("Please enter a valid salary"),
            gender = input.gender,
            email = input.email
        )
        newEmployee.id = UUID.randomUUID().toString()
        return employeesRepository.save(newEmployee)
    }
}

data class EmployeeInput(
    val name: String?,
    val dateOfBirth: String?,
    val city: String?,
    val salary: Float?,
    val gender: String?,
    val email: String?
)
