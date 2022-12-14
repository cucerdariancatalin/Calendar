package com.simplemobiletools.calendar.pro.dialogs

import androidx.appcompat.app.AlertDialog
import com.simplemobiletools.calendar.pro.R
import com.simplemobiletools.calendar.pro.activities.SimpleActivity
import com.simplemobiletools.calendar.pro.adapters.FilterEventTypeAdapter
import com.simplemobiletools.calendar.pro.extensions.config
import com.simplemobiletools.calendar.pro.extensions.eventsHelper
import com.simplemobiletools.commons.extensions.getAlertDialogBuilder
import com.simplemobiletools.commons.extensions.setupDialogStuff
import kotlinx.android.synthetic.main.dialog_filter_event_types.view.*

class SelectQuickFilterEventTypesDialog(val activity: SimpleActivity) {
    private var dialog: AlertDialog? = null
    private val view = activity.layoutInflater.inflate(R.layout.dialog_filter_event_types, null)

    init {
        activity.eventsHelper.getEventTypes(activity, false) {
            val quickFilterEventTypes = activity.config.quickFilterEventTypes
            view.filter_event_types_list.adapter = FilterEventTypeAdapter(activity, it, quickFilterEventTypes)

            activity.getAlertDialogBuilder()
                .setPositiveButton(R.string.ok) { dialogInterface, i -> confirmEventTypes() }
                .setNegativeButton(R.string.cancel, null)
                .apply {
                    activity.setupDialogStuff(view, this) { alertDialog ->
                        dialog = alertDialog
                    }
                }
        }
    }

    private fun confirmEventTypes() {
        val selectedItems = (view.filter_event_types_list.adapter as FilterEventTypeAdapter).getSelectedItemsList().map {
            it.toString()
        }.toHashSet()

        if (activity.config.quickFilterEventTypes != selectedItems) {
            activity.config.quickFilterEventTypes = selectedItems
        }
        dialog?.dismiss()
    }
}
