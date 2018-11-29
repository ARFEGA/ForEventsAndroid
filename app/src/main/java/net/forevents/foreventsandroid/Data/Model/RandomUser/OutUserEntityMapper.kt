package net.forevents.foreventsandroid.Data.CreateUser.RandomUser



import net.forevents.foreventsandroid.Data.CreateUser.Mapper


class OutUserEntityMapper :
    Mapper<RandomUser, UserEntity> {
    override fun transform(input: RandomUser): UserEntity =
        //UserEntity("Juan","https://randomuser.me/api/portraits/women/30.jpg")
        UserEntity(
            input.name.last.toString(),
            input.picture.large.toString()
        )

    override fun transformList(inputList: List<RandomUser>): List<UserEntity> =
            inputList.map {
                transform(it) }

}