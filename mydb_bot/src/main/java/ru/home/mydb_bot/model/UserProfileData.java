package ru.home.mydb_bot.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "userProfileData")
public class UserProfileData implements Serializable {
    @Id
    String id;
    String name, surname, city, gender, email, married, hasChild;
    int age;
    long cellphone,chatId;

    @Override
    public String toString() {
        return String.format("Имя: %s%nФамилия: %s%nПол: %s%nВозраст: %d%nE-mail: %s%nГород проживания: %s%n" +
                "Номер телефона: %d%nСемейное положение: %s%nДети: %s%n", getName(),getSurname(),getGender(),getAge(),
                 getEmail(),getCity(),getCellphone(),getMarried(),getHasChild());
    }
}
