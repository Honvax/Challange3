package com.alfrsms.challange3.word

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.alfrsms.challange3.R

class WordAdapter(private val letterId: String, context: Context) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    private val filteredWords: List<String>

    init {
        val words = context.resources.getStringArray(R.array.words).toList()

        filteredWords = words
            .filter { it.startsWith(letterId, ignoreCase = true) }
            .shuffled()
            .take(3)
            .sorted()
    }

    class WordViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val button: Button = view.findViewById(R.id.button_item)
    }

    override fun getItemCount(): Int = filteredWords.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        layout.accessibilityDelegate = Accessibility
        return WordViewHolder(layout)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {

        val item = filteredWords[position]
        val context = holder.view.context

        holder.button.text = item
        holder.button.setOnClickListener{
            val queryUrl: Uri = Uri.parse("${WordFragment.SEARCH_PREFIX}${item}")
            val intent = Intent(Intent.ACTION_VIEW, queryUrl)
            context.startActivity(intent)
        }

    }

    companion object Accessibility : View.AccessibilityDelegate() {
        override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            val customString = host?.context?.getString(R.string.cari_kata)
            val customClick =
                AccessibilityNodeInfo.AccessibilityAction(
                    AccessibilityNodeInfo.ACTION_CLICK,
                    customString
                )
            info?.addAction(customClick)
        }
    }
}