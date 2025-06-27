package r.team.unpluggedproviderapp.presentation.feature_devices

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import r.team.unpluggedproviderapp.databinding.ListItemDeviceBinding
import r.team.unpluggedproviderapp.domain.model.DeviceResponseDO

private class DeviceDiffUtil : DiffUtil.ItemCallback<DeviceResponseDO>() {
    override fun areItemsTheSame(oldItem: DeviceResponseDO, newItem: DeviceResponseDO) =
        oldItem === newItem

    override fun areContentsTheSame(oldItem: DeviceResponseDO, newItem: DeviceResponseDO) =
        oldItem == newItem

}

class DevicesAdapter :
    ListAdapter<DeviceResponseDO, DevicesAdapter.DeviceViewHolder>(DeviceDiffUtil()) {

    inner class DeviceViewHolder(private val binding: ListItemDeviceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: DeviceResponseDO) {
            binding.itemIdTextview.text = "ID-${item.id}"
            binding.itemNameTextview.text = "Name-${item.name}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DeviceViewHolder(
        ListItemDeviceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }
}