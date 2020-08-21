package my.projects.seasonalanimetracker.auth.viewobject

interface IAuthVO {
    fun authStatus(): AuthStatus
}

class AuthVO(private val authStatus: AuthStatus): IAuthVO {

    override fun authStatus(): AuthStatus {
        return authStatus
    }

}