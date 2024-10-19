package com.ardacolak.fotografpaylasma.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardacolak.fotografpaylasma.model.Post
import com.ardacolak.fotografpaylasma.R
import com.ardacolak.fotografpaylasma.adapter.PostAdapter
import com.ardacolak.fotografpaylasma.databinding.FragmentFeedBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore


class FeedFragment : Fragment(),PopupMenu.OnMenuItemClickListener {
    private var _binding: FragmentFeedBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    private lateinit var popup:PopupMenu
    private lateinit var auth:FirebaseAuth
    private lateinit var db: FirebaseFirestore
    val postList : ArrayList<Post> = arrayListOf()
    private var adapter : PostAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth=Firebase.auth
        db=Firebase.firestore
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.floatingActionButton.setOnClickListener { floatingButtonTiklandi(it) }
        popup=PopupMenu(requireContext(),binding.floatingActionButton)
        val inflater=popup.menuInflater
        inflater.inflate(R.menu.mp_popup_menu,popup.menu)
        popup.setOnMenuItemClickListener(this)

        fireStoreVerileriAl()

        adapter= PostAdapter(postList)
        binding.feedRecyclerView.adapter=adapter
        binding.feedRecyclerView.layoutManager=LinearLayoutManager(requireContext())
    }

    fun fireStoreVerileriAl(){
        db.collection("Posts").addSnapshotListener { value, error ->
            if(error !=null){
                Toast.makeText(requireContext(),error.localizedMessage,Toast.LENGTH_LONG)
            }else{
                if(value!=null){
                    if (!value.isEmpty){
                        postList.clear()
                        val documents = value.documents
                        for (documents in documents){
                            val comment = documents.get("comment") as String
                            val email= documents.get("email") as String
                            val downlandUrl=documents.get("dowlandUrl") as String
                            val post = Post(email,comment,downlandUrl)
                            postList.add(post)

                        }
                        adapter?.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    fun floatingButtonTiklandi(view: View){
        popup.show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item?.itemId== R.id.yuklemeItem){
            val action=FeedFragmentDirections.actionFeedFragmentToYuklemeFragment()
            Navigation.findNavController(requireView()).navigate(action)
        }else if (item?.itemId== R.id.cikisItem){
            auth.signOut()
            val action=FeedFragmentDirections.actionFeedFragmentToKullaniciFragment()
            Navigation.findNavController(requireView()).navigate(action)
        }
        return true
    }

}