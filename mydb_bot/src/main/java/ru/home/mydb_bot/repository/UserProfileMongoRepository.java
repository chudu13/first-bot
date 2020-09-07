package ru.home.mydb_bot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.home.mydb_bot.model.UserProfileData;
import java.util.List;

@Repository
public interface UserProfileMongoRepository extends MongoRepository<UserProfileData,String> {
    List<UserProfileData> findByChatId(long chatId);

    void deleteByNameAndSurname(String name, String surname);

    List<UserProfileData> findAll();

    List<UserProfileData> findByNameAndSurname(String name, String surname);

    List<UserProfileData> findByName(String name);

    List<UserProfileData> findBySurname(String surname);

    List<UserProfileData> findByCity(String city);

    List<UserProfileData> findByAge(int age);

}