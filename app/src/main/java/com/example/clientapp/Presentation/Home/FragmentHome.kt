package com.example.clientapp.Presentation.Home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientapp.Domain.Model.Model.Trip
import com.example.clientapp.Domain.Repository.ItemListener
import com.example.clientapp.Presentation.BookTicket.BookTicketActivity
import com.example.clientapp.Presentation.MapTripClient.MapActivity
import com.example.clientapp.Presentation.SelectLocation.SelectLocationActivity
import com.example.clientapp.R
import com.example.clientapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import showDateTimeDialog
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class FragmentHome : Fragment(), ItemListener {
    private var _binding: FragmentHomeBinding? = null
    private val fragmentHomeViewModel: FragmentHomeViewModel by viewModels()
    private val binding get() = _binding!!
    private var departureDate: Long? = null
    private val REQUEST_CODE_DIEM_DON =1000
    private val REQUEST_CODE_DIEM_DEN =1001
    private var roundTrip = false
    private var returnDate: Long? = null
    private lateinit var adapter: TrackingTripAdapter
    private val listTrip: MutableList<Trip> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TrackingTripAdapter(mutableListOf(), this)
        binding.rvTracking.adapter = adapter
        binding.rvTracking.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        btnSearchOnClick()
        btnRoundTripOnClick()
        inputLocation()
        toTrackingMap()
        getTrip()
    }

    private fun getTrip() {
        fragmentHomeViewModel.getTrip()
    }

    private fun toTrackingMap() {
    }

    private fun inputLocation() {
        binding.diemDon.setOnClickListener{
            val intent = Intent(context, SelectLocationActivity::class.java)
            intent.putExtra("checkLocation", 0)
            startActivityForResult(intent, REQUEST_CODE_DIEM_DON)
        }
        binding.diemDen.setOnClickListener{
            val intent = Intent(context, SelectLocationActivity::class.java)
            intent.putExtra("checkLocation", 1)
            startActivityForResult(intent, REQUEST_CODE_DIEM_DEN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_DIEM_DON -> {
                    val diemDon = data?.getStringExtra("location")
                    binding.diemDon.setText(diemDon)
                }
                REQUEST_CODE_DIEM_DEN -> {
                    val diemDen = data?.getStringExtra("location")
                    binding.diemDen.setText(diemDen)
                }
            }
        }
    }
    private fun btnRoundTripOnClick() {
        binding.roundTrip.setOnCheckedChangeListener{ buttonView,isChecked->
            if(isChecked){
                binding.tvTitle5.visibility = View.VISIBLE
                binding.tvTitle6.visibility = View.VISIBLE
                binding.chonNgayVe.visibility = View.VISIBLE
                roundTrip = true
            }
            else{
                binding.tvTitle5.visibility = View.GONE
                binding.tvTitle6.visibility = View.GONE
                binding.chonNgayVe.visibility = View.GONE
                roundTrip = false
            }
        }
        binding.chonNgayDi.setOnClickListener{
            showDateTimeDialog(requireContext()){selectDate ->
                binding.chonNgayDi.setText(selectDate)
                departureDate = convertDateToMillis(selectDate)
                // Kiểm tra nếu ngày đi sau ngày về thì xóa ngày về
                if (returnDate != null &&roundTrip==true && departureDate!! > returnDate!!) {
                    binding.chonNgayVe.text = ""
                    returnDate = null
                    Toast.makeText(context, "Ngày đi không được sau ngày về. Vui lòng chọn lại ngày về.", Toast.LENGTH_SHORT).show()
                }
            }
        }
        // Chọn ngày về (chỉ hiển thị khi chế độ khứ hồi được chọn)
        binding.chonNgayVe.setOnClickListener {
            departureDate?.let { depDate ->
                // Đảm bảo ngày khứ hồi phải sau ngày đi
                showDateTimeDialog(requireContext(), depDate) { selectDate ->
                    binding.chonNgayVe.setText(selectDate)
                    returnDate = convertDateToMillis(selectDate)

                    // Kiểm tra nếu ngày về trước ngày đi
                    if (returnDate!! < depDate) {
                        Toast.makeText(context, "Ngày về phải sau ngày đi. Vui lòng chọn lại.", Toast.LENGTH_SHORT).show()
                        binding.chonNgayVe.text = ""
                        returnDate = null
                    }
                }
            } ?: run {
                // Thông báo nếu chưa chọn ngày đi
                Toast.makeText(context, "Vui lòng chọn ngày đi trước", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun btnSearchOnClick() {
        binding.btnSearch.setOnClickListener{
            if(binding.diemDon.text.isEmpty() || binding.diemDen.text.isEmpty()){
                Toast.makeText(context, "Vui lòng chọn điểm đón và điểm đến", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else{
                if(roundTrip==false){
                    if(binding.chonNgayDi.text.isEmpty()){
                        Toast.makeText(context, "Vui lòng chọn ngày đi", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                }
                else{
                    if(binding.chonNgayDi.text.isEmpty() || binding.chonNgayVe.text.isEmpty()){
                        Toast.makeText(context, "Vui lòng chọn ngày đi và ngày về", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                }
            }
            fragmentHomeViewModel.setPickOffPoint(binding.diemDon.text.toString())
            fragmentHomeViewModel.setDropOffPoint(binding.diemDen.text.toString())
            fragmentHomeViewModel.setDepartureDate(binding.chonNgayDi.text.toString())
            fragmentHomeViewModel.setReturnDate(binding.chonNgayVe.text.toString())
            fragmentHomeViewModel.setRoundTrip(roundTrip)
            sendBroadcast()
        }
    }
    private fun convertDateToMillis(date: String): Long {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.parse(date)?.time ?: System.currentTimeMillis()
    }
    private fun sendBroadcast() {
        val pickUpPoint = binding.diemDon.text.toString()
        val dropOffPoint = binding.diemDen.text.toString()
        val departureDate = binding.chonNgayDi.text.toString()
        val returnDate = binding.chonNgayVe.text.toString()
        // Tạo Intent để gửi dữ liệu
        val intent = Intent(context, BookTicketActivity::class.java)
        intent.putExtra("pickUpPoint", pickUpPoint)
        intent.putExtra("dropOffPoint", dropOffPoint)
        intent.putExtra("departureDate", departureDate)
        intent.putExtra("returnDate", returnDate)
        intent.putExtra("roundTrip", roundTrip)
        // Gửi broadcast
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        fragmentHomeViewModel.getTrip()
        fragmentHomeViewModel.countPayment()
        fragmentHomeViewModel.countPayment.observe(viewLifecycleOwner,{count ->
            if(count== null || count == 0L){
                binding.txtNoti.visibility= View.GONE
            }
            else{
                binding.tvNoti.text = "Bạn có ${count} giao dịch chờ thanh toán"
                binding.txtNoti.visibility = View.VISIBLE
            }
        })
        fragmentHomeViewModel.listTrip.observe(viewLifecycleOwner,{trip ->
            listTrip.clear()
            listTrip.addAll(trip)
            adapter.updateList(listTrip)
        })
    }

    override fun onItemClick(position: Int) {
        val trip = listTrip[position]
        val intent = Intent(context, MapActivity::class.java)
        intent.putExtra("trip", trip)
        startActivity(intent)
    }

    override fun onItemClickSelected(position: Int, isSelected: Boolean) {
    }
}