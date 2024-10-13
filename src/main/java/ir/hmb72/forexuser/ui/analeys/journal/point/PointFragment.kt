package ir.hmb72.forexuser.ui.analeys.journal.point

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ir.hmb72.forexuser.databinding.FragmentPointBinding
import ir.hmb72.forexuser.utils.BUNDLE_ID_POINT
import ir.hmb72.forexuser.utils.DELETE
import ir.hmb72.forexuser.utils.EDIT
import ir.hmb72.forexuser.utils.MOVE
import ir.hmb72.forexuser.utils.setupRecyclerView
import ir.hmb72.forexuser.viewmodel.journal.point.PointViewModel
import javax.inject.Inject


@AndroidEntryPoint
class PointFragment : Fragment() {
    //Binding
    private var _binding: FragmentPointBinding? = null
    private val binding get() = _binding

    //Other
    @Inject
    lateinit var adapterPoint: AdapterPoint


    private val viewModel: PointViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPointBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding?.apply {

            //Open Add point fragment
            openAddPointBtn.setOnClickListener {
                AddPointFragment().show(childFragmentManager, AddPointFragment().tag)
            }

            //Show data
            viewModel.getAllPoint()
            viewModel.showAllData.observe(viewLifecycleOwner) {
                adapterPoint.setData(it)
                recyclerPointFrg.setupRecyclerView(
                    LinearLayoutManager(requireContext()),
                    adapterPoint
                )
            }

            adapterPoint.clickItem { pointEntity, type ->

                when (type){
                    MOVE->{
                        val direction =PointFragmentDirections.actionPointFragmentToDetailPointFragment(pointEntity.id)
                        findNavController().navigate(direction)

                    }
                    EDIT->{
                        val addPointFragment = AddPointFragment()
                        val bundle = Bundle()
                        bundle.putInt(BUNDLE_ID_POINT , pointEntity.id)
                        addPointFragment.arguments = bundle
                        addPointFragment.show(childFragmentManager , AddPointFragment().tag)
                    }
                    DELETE->{
                        viewModel.delete(pointEntity)
                    }

                    else -> {}
                }


            }

            //Back
            backBtn.setOnClickListener { findNavController().navigateUp() }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}