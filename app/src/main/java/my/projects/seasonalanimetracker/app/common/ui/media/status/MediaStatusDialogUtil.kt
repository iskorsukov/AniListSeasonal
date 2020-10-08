package my.projects.seasonalanimetracker.app.common.ui.media.status

import android.content.Context
import androidx.appcompat.app.AlertDialog
import my.projects.seasonalanimetracker.R
import my.projects.seasonalanimetracker.app.common.data.media.Media
import my.projects.seasonalanimetracker.following.data.MediaListAction

object MediaStatusDialogUtil {

    fun createSetMediaStatusActionDialog(context: Context, item: Media, listener: SelectMediaListActionListener): AlertDialog {
        val labels = context.resources.getStringArray(R.array.media_list_action_labels)
        val actions = context.resources.getStringArray(R.array.media_list_actions)
        return AlertDialog.Builder(context)
            .setTitle(R.string.select_action)
            .setItems(labels.copyOfRange(0, labels.size - 1)) { dialog, pos ->
                dialog?.dismiss()
                val action = MediaListAction.valueOf(actions[pos].toUpperCase())
                listener.onMediaStatusSelected(item, action)
                dialog?.dismiss()
            }
            .setCancelable(true)
            .create()
    }

    fun createModifyMediaStatusActionDialog(context: Context, item: Media, listener: SelectMediaListActionListener): AlertDialog {
        val labels = context.resources.getStringArray(R.array.media_list_action_labels)
        val actions = context.resources.getStringArray(R.array.media_list_actions)
        return AlertDialog.Builder(context)
            .setTitle(R.string.select_action)
            .setItems(labels) { dialog, pos ->
                dialog?.dismiss()
                val action = MediaListAction.valueOf(actions[pos].toUpperCase())
                if (action == MediaListAction.REMOVE) {
                    showRemoveDialog(context, item, listener)
                    dialog?.dismiss()
                } else {
                    listener.onMediaStatusSelected(item, action)
                    dialog?.dismiss()
                }
            }
            .setCancelable(true)
            .create()
    }

    private fun showRemoveDialog(context: Context, item: Media, listener: SelectMediaListActionListener) {
        val dialog = AlertDialog.Builder(context)
            .setMessage(R.string.remove_message)
            .setPositiveButton(R.string.yes) { dialog, _ ->
                listener.onMediaStatusSelected(item, MediaListAction.REMOVE)
                dialog?.dismiss()
            }
            .setNegativeButton(R.string.no) { dialog, _ ->
                dialog?.dismiss()
            }
            .setCancelable(false)
            .create()
        dialog.show()
    }
}