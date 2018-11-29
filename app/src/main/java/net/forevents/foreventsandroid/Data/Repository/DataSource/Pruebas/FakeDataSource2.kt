package net.forevents.foreventsandroid.Data.Repository.DataSource.Pruebas


class FakeDataSource2/*: DataSource*/ {
  /*  override fun createUser(): Single<AppCreateUser> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserToken(): Flowable<AppUser> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    val userList = listOf(
        UserEntity(
            // 6,
            "sarah",
            //"torsvik",
            "https://randomuser.me/api/portraits/women/30.jpg"
            //"arfega.anisgaro@gmail.com"
        ),
        UserEntity(
            //7,
            "simon",
            //"colin",
            "https://randomuser.me/api/portraits/men/24.jpg"
            //"arfega.anisgaro@gmail.com"
        )
    )

    override fun getUserList(): Flowable<List<UserEntity>> = Flowable.just(userList)

   /* override fun getUserdetail(userId: Long): Observable<UserEntity> {
        val user = userList.find { it.userId == userId }
        return if (user != null) {
            Observable.just(user)
        }else{
            Observable.empty()
        }
    }*/
    */
}