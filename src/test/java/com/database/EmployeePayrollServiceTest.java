package com.database;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static com.database.EmployeePayrollService.IOService.DB_IO;
import static com.database.EmployeePayrollService.IOService.FILE_IO;

public class EmployeePayrollServiceTest {
    @Test
    public void given3EmployeeWhenWrittenToFile_shouldMatchEmployeeEntries() {
        EmployeePayrollData[] employeeData = {
                new EmployeePayrollData(1,"Jeff Bezos", 100000.0),
                new EmployeePayrollData(2,"Bill Gates",200000.0),
                new EmployeePayrollData(3,"Mark Zuckerberg",300000.0)
        };

        EmployeePayrollService employeePayrollService;
        employeePayrollService = new EmployeePayrollService(Arrays.asList(employeeData));
        employeePayrollService.writeEmployeePayrollData(FILE_IO);
        employeePayrollService.printData(FILE_IO);
        long entries = employeePayrollService.countEntries(FILE_IO);
        Assertions.assertEquals(3,entries);
    }

    @Test
    public void givenEmployeePayrollInDB_whenRetrieved_shouldMatchEmployeeCount(){
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollDataDB(DB_IO);
        Assertions.assertEquals(3,employeePayrollData.size());
    }
}
