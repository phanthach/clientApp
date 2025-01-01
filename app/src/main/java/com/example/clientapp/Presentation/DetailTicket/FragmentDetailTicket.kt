package com.example.clientapp.Presentation.DetailTicket

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.clientapp.Domain.Model.Model.Ticket
import com.example.clientapp.Domain.Service.PDFSaveService
import com.example.clientapp.R
import com.example.clientapp.databinding.FragmentDetailTicketBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class FragmentDetailTicket: Fragment() {
    private var _binding: FragmentDetailTicketBinding? = null
    private val binding get() = _binding!!
    private val fragmentDetailTicketViewModel: FragmentDetailTicketViewModel by viewModels()
    private val detailTicketActivityViewModel: DetailTicketActivityViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailTicketBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility = View.VISIBLE
        binding.btnPrint.isEnabled = false
        getTicketDetail()
        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.btnPrint.setOnClickListener {
            // Đảm bảo rằng dữ liệu đã được tải đầy đủ trước khi lưu
            if (fragmentDetailTicketViewModel.ticketResponse.value?.ticket != null) {
                saveScrollViewToPDF(binding.ttScrollView)
            } else {
                Toast.makeText(requireContext(), "Dữ liệu chưa sẵn sàng", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun getTicketDetail() {
        val ticket: Ticket = detailTicketActivityViewModel.ticket.value!!
        if(ticket.status == 2){
            binding.btnPrint.isEnabled = true
            binding.btnPrint.setBackgroundResource(R.drawable.button_green_border)
        }
        else{
            binding.btnPrint.isEnabled = false
            binding.btnPrint.setBackgroundResource(R.drawable.boder_diss)
        }
        fragmentDetailTicketViewModel.getTicketDetail(ticket.ticketId)
        fragmentDetailTicketViewModel.ticketResponse.observe(viewLifecycleOwner, {ticketResponse->
            if(ticketResponse== null || ticketResponse.ticket == null){
                binding.progressBar.visibility = View.GONE
                binding.tvError.visibility = View.VISIBLE
            }
            else{
                binding.progressBar.visibility = View.GONE
                binding.tvError.visibility = View.GONE
                if(ticketResponse.ticket!!.status == 2){
                    Glide.with(binding.root)
                        .load(ticketResponse.ticket!!.qrCode)
                        .placeholder(R.drawable.logo)
                        .error(R.drawable.logo)
                        .into(binding.qrCode)
                }
                binding.tvTicketCode.text = ticketResponse.ticket!!.ticketCode
                binding.tvNameVehicle.text = ticketResponse.trip!!.nameVehicle
                binding.tvPlateNumber.text = ticketResponse.vehicle!!.plateNumber
                binding.tvPickUp.text = ticketResponse.pickupLocation!!.nameLocation
                binding.tvTimePickUp.text = ticketResponse.pickupLocation!!.arrivalTime
                binding.tvNameSeat.text = ticketResponse.seat!!.nameSeat
                binding.tvPrice.text = ticketResponse.ticket!!.ticketPrice.toString()
                val date = convertDateFormat(ticketResponse.ticket!!.bookingTime)
                binding.tvXN.text = date
                binding.ttScrollView.visibility = View.VISIBLE
                binding.btnPrint.isEnabled = true
            }
        })
    }
    fun convertDateFormat(inputDate: String): String {
        // Định dạng gốc của chuỗi ngày tháng
        val inputFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
        // Định dạng mong muốn
        val outputFormat = SimpleDateFormat("'Ngày' dd 'tháng' MM 'năm' yyyy", Locale.getDefault())

        return try {
            // Chuyển chuỗi thành đối tượng Date
            val date: Date = inputFormat.parse(inputDate) ?: return "Ngày không hợp lệ"
            // Định dạng lại ngày tháng
            outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            "Ngày không hợp lệ"
        }
    }
    private fun saveScrollViewToPDF(scrollView: ScrollView) {
        // Tạo bitmap từ ScrollView
        val bitmap = Bitmap.createBitmap(scrollView.width, scrollView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        scrollView.draw(canvas)

        // Lưu bitmap vào file tạm thời
        val file = File(requireContext().cacheDir, "temp_ticket_${System.currentTimeMillis()}.png")
        try {
            FileOutputStream(file).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Lỗi khi lưu file tạm thời", Toast.LENGTH_SHORT).show()
            return
        }

        // Gửi đường dẫn file đến Service
        val intent = Intent(requireContext(), PDFSaveService::class.java).apply {
            putExtra(PDFSaveService.EXTRA_FILE_PATH, file.absolutePath)
        }
        requireContext().startService(intent)

        Toast.makeText(requireContext(), "Đang lưu PDF...", Toast.LENGTH_SHORT).show()
    }



}