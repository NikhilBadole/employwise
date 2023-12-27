package com.example.EmployWise.Model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "employee")
public class Employee {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "employeeName")
    String employeeName;

    @Column(name = "phoneNumber")
    String phoneNumber;

    @Column(name = "email")
    String email;

    @Column(name = "reportsTo")
    int reportsTo;

    @Column(name = "profileImage")
    String profileImage;
}
