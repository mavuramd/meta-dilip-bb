SUMMARY = "Dynamic Network config for eth0 with systemd"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"


do_fetch[noexec] = "1"
do_unpack[noexec] = "1"
do_patch[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    touch ${S}/20-dynamic-eth0.network

    echo "[Match]"   >> ${S}/20-dynamic-eth0.network
    echo "Name=eth0" >> ${S}/20-dynamic-eth0.network
    echo "  "        >> ${S}/20-dynamic-eth0.network
    echo "[Network]" >> ${S}/20-dynamic-eth0.network
    echo "DHCP=yes"  >> ${S}/20-dynamic-eth0.network
    echo "  "        >> ${S}/20-dynamic-eth0.network

    install -d ${D}/etc/systemd/network/
    install -m 666 ${S}/20-dynamic-eth0.network ${D}/etc/systemd/network/20-dynamic-eth0.network
}
