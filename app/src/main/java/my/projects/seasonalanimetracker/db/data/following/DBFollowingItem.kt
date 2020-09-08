package my.projects.seasonalanimetracker.db.data.following

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import my.projects.seasonalanimetracker.db.data.media.DBMedia

@Entity(
    tableName = "following",
    foreignKeys = [
        ForeignKey(
            entity = DBMedia::class,
            parentColumns = ["id"],
            childColumns = ["mediaId"]
        )
    ]
)
data class DBFollowingItem(
    @PrimaryKey
    val id: Long,
    val mediaId: Long,
    val status: String
)