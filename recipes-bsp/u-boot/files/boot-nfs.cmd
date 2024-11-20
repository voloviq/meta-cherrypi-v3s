# Default to (primary) SD
setenv bootargs console=${console} console=tty1 root=/dev/nfs rw ip=dhcp nfsroot=192.168.0.139:/home/mw/nfs/voice-nfs,v3,tcp
bootz ${kernel_addr_r} - ${fdt_addr_r} || bootm ${kernel_addr_r} - ${fdt_addr_r}
