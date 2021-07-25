package com.androiddevs.ktornoteapp.ui.notedetail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.androiddevs.ktornoteapp.R
import com.androiddevs.ktornoteapp.data.local.entities.Note
import com.androiddevs.ktornoteapp.other.Status
import com.androiddevs.ktornoteapp.ui.BaseFragment
import com.androiddevs.ktornoteapp.ui.dialogs.AddOwnerDialog
import dagger.hilt.android.AndroidEntryPoint
import io.noties.markwon.Markwon
import kotlinx.android.synthetic.main.fragment_note_detail.*

/**
 *Veli-Matti Tikkanen, 24.6.2021
 */

const val ADD_OWNER_DIALOG_TAG = "ADD_OWNER_DIALOG_TAG"

@AndroidEntryPoint
class NoteDetailFragment: BaseFragment(R.layout.fragment_note_detail) {

    private val args: NoteDetailFragmentArgs by navArgs()

    private val viewModel: NoteDetailViewModel by viewModels()

    private var curNote: Note? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObserver()
        fabEditNote.setOnClickListener {
            findNavController().navigate(NoteDetailFragmentDirections.actionNoteDetailFragmentToAddEditNoteFragment(args.id))
        }

        if(savedInstanceState != null){
            val addOwnerDialog = parentFragmentManager.findFragmentByTag(ADD_OWNER_DIALOG_TAG)
                as AddOwnerDialog?
            addOwnerDialog?.setPositiveListener {
                addOwnerToCurNote(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.note_detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.miAddOwner -> showAddOwnerDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAddOwnerDialog(){
        AddOwnerDialog().apply {
            setPositiveListener {
                addOwnerToCurNote(it)
            }
        }.show(parentFragmentManager, ADD_OWNER_DIALOG_TAG)
    }

    private fun addOwnerToCurNote(email: String) {
        curNote?.let { note ->
            viewModel.addOwnerToNote(email, note.id)
        }
    }

    private fun setMarkdownText(text: String){
        val markwon = Markwon.create(requireContext())
        val markdown = markwon.toMarkdown(text)
        markwon.setParsedMarkdown(tvNoteContent, markdown)
    }

    private fun subscribeToObserver(){
        viewModel.addOwnerStatus.observe(viewLifecycleOwner, Observer { event ->
                event?.getContentIfNotHandled()?.let { result ->
                    when(result.status){
                        Status.SUCCESS -> {
                            addOwnerProgressBar.visibility = View.GONE
                            showSnackbar(result.data ?: "Successfully added owner to note")
                        }
                        Status.LOADING -> {
                            addOwnerProgressBar.visibility = View.VISIBLE
                        }
                        Status.ERROR -> {
                            addOwnerProgressBar.visibility = View.GONE
                            showSnackbar(result.message ?: "An unknown error occured")
                        }
                    }
                }
        })
        viewModel.observeNoteByID(args.id).observe(viewLifecycleOwner, Observer {
            it?.let { note ->
                tvNoteTitle.text = note.title
                setMarkdownText(note.content)
                curNote = note
            } ?: showSnackbar("Note not found")
        })
    }

}