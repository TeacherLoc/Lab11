package com.example.qlchitieu.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qlchitieu.R
import com.example.qlchitieu.database.Transaction
import com.example.qlchitieu.adapter.TransactionAdapter
import kotlin.random.Random

class BudgetFragment : Fragment(R.layout.fragment_budget) {

    private var currentAmount: Double = 0.0
    private lateinit var transactionAdapter: TransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_budget, container, false)

        val currentAmountTextView: TextView = view.findViewById(R.id.currentAmountTextView)
        val randomTransactionImageView: ImageView = view.findViewById(R.id.randomTransactionImageView)
        val transactionRecyclerView: RecyclerView = view.findViewById(R.id.transactionRecyclerView)

        // Cấu hình RecyclerView
        transactionAdapter = TransactionAdapter()
        transactionRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        transactionRecyclerView.adapter = transactionAdapter

        // Hiển thị số tiền hiện tại
        currentAmountTextView.text = "Số tiền: ₫${String.format("%.2f", currentAmount)}"

        // Xử lý sự kiện nhấn vào hình ảnh
        randomTransactionImageView.setOnClickListener {
            val randomAmount = Random.nextDouble(10.0, 100.0)
            val isAddition = Random.nextBoolean()

            val type: String
            val description: String

            if (isAddition) {
                currentAmount += randomAmount
                type = "Thu nhập"
                description = "Cộng thêm tiền ngẫu nhiên"
                Toast.makeText(requireContext(), "Bạn vừa được cộng ₫${String.format("%.2f", randomAmount)}!", Toast.LENGTH_SHORT).show()
            } else {
                currentAmount -= randomAmount
                type = "Chi tiêu"
                description = "Trừ tiền ngẫu nhiên"
                Toast.makeText(requireContext(), "Bạn vừa bị trừ ₫${String.format("%.2f", randomAmount)}!", Toast.LENGTH_SHORT).show()
            }

            // Tạo giao dịch mới
            val transaction = Transaction(
                id = System.currentTimeMillis().toString(),
                amount = randomAmount,
                description = description,
                type = type,
                date = System.currentTimeMillis()
            )

            // Cập nhật RecyclerView thông qua adapter
            transactionAdapter.addTransaction(transaction)

            // Cập nhật số tiền hiện tại
            currentAmountTextView.text = "Số tiền: ₫${String.format("%.2f", currentAmount)}"
        }

        return view
    }
}

