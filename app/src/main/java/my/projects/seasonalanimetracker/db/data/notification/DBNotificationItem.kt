package my.projects.seasonalanimetracker.db.data.notification

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import my.projects.seasonalanimetracker.db.data.media.DBMedia

@Entity(tableName = "notifications",
    foreignKeys = [
        ForeignKey(
            entity = DBMedia::class,
            parentColumns = ["id"],
            childColumns = ["mediaId"]
        )
    ]
)
data class DBNotificationItem(
    @PrimaryKey
    val id: Long,
    val mediaId: Long,
    val createdAt: Long,
    val episode: Int
)