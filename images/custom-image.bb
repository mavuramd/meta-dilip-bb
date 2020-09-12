SUMMARY = "A Cutom development image"
HOMEPAGE = "https://github.com/mavuramd"

IMAGE_FEATURES += "package-management"
IMAGE_LINGUAS = "en-us"

inherit image

CORE_OS = " \
    openssh openssh-keygen openssh-sftp-server \
    packagegroup-core-boot \
    tzdata \
"

KERNEL_EXTRA_INSTALL = " \
    kernel-modules \
"

EXTRA_TOOLS_INSTALL = " \
    bzip2 \
    curl \
    cursor-blink \
    devmem2 \
    dosfstools \
    e2fsprogs-mke2fs \
    emmc-upgrader \
    ethtool \
    findutils \
    grep \
    i2c-tools \
    ifupdown \
    iperf3 \
    iproute2 \
    iptables \
    less \
    lsof \
    nano \
    netcat-openbsd \
    nmap \
    ntp ntp-tickadj \
    parted \
    procps \
    sysfsutils \
    tcpdump \
    tree \
    unzip \
    util-linux \
    util-linux-blkid \
    wget \
    xz \
    zip \
"

INSTALL_MISC = " \
    emmc-self-installer \
"

WIFI = " \
    crda \
    linux-firmware-rtl8192e \
    wpa-supplicant \
"

IMAGE_INSTALL += " \
    ${CORE_OS} \
    ${EXTRA_TOOLS_INSTALL} \
    ${KERNEL_EXTRA_INSTALL} \
    ${INSTALL_MISC} \
    ${WIFI} \
"

set_local_timezone() {
    ln -sf /usr/share/zoneinfo/CET ${IMAGE_ROOTFS}/etc/localtime
}

disable_bootlogd() {
    echo BOOTLOGD_ENABLE=no > ${IMAGE_ROOTFS}/etc/default/bootlogd
}

enable_dhcp_eth0() {
    mkdir -p ${IMAGE_ROOTFS}/etc/systemd/network/

    touch ${IMAGE_ROOTFS}/etc/systemd/network/20-dynamic-eth0.network

    echo "[Match]"   >> ${IMAGE_ROOTFS}/etc/systemd/network/20-dynamic-eth0.network
    echo "Name=eth0" >> ${IMAGE_ROOTFS}/etc/systemd/network/20-dynamic-eth0.network
    echo "  "        >> ${IMAGE_ROOTFS}/etc/systemd/network/20-dynamic-eth0.network
    echo "[Network]" >> ${IMAGE_ROOTFS}/etc/systemd/network/20-dynamic-eth0.network
    echo "DHCP=yes"  >> ${IMAGE_ROOTFS}/etc/systemd/network/20-dynamic-eth0.network

    chmod 666 ${IMAGE_ROOTFS}/etc/systemd/network/20-dynamic-eth0.network
}

enable_dhcp_wlan0() {
    mkdir -p ${IMAGE_ROOTFS}/etc/systemd/network/

    touch ${IMAGE_ROOTFS}/etc/systemd/network/21-dynamic-wlan0.network

    echo "[Match]"    >> ${IMAGE_ROOTFS}/etc/systemd/network/21-dynamic-wlan0.network
    echo "Name=wlan0" >> ${IMAGE_ROOTFS}/etc/systemd/network/21-dynamic-wlan0.network
    echo "  "         >> ${IMAGE_ROOTFS}/etc/systemd/network/21-dynamic-wlan0.network
    echo "[Network]"  >> ${IMAGE_ROOTFS}/etc/systemd/network/21-dynamic-wlan0.network
    echo "DHCP=ipv4"  >> ${IMAGE_ROOTFS}/etc/systemd/network/21-dynamic-wlan0.network

    chmod 666 ${IMAGE_ROOTFS}/etc/systemd/network/21-dynamic-wlan0.network
}

wpa_supplicant_wlan0() {
    mkdir -p ${IMAGE_ROOTFS}/etc/wpa_supplicant/

    touch ${IMAGE_ROOTFS}/etc/wpa_supplicant/wpa_supplicant-wlan0.conf

    echo "ctrl_interface=/var/run/wpa_supplicant" >> ${IMAGE_ROOTFS}/etc/wpa_supplicant/wpa_supplicant-wlan0.conf
    echo "eapol_version=1"                        >> ${IMAGE_ROOTFS}/etc/wpa_supplicant/wpa_supplicant-wlan0.conf
    echo "ap_scan=1"                              >> ${IMAGE_ROOTFS}/etc/wpa_supplicant/wpa_supplicant-wlan0.conf
    echo "fast_reauth=1"                          >> ${IMAGE_ROOTFS}/etc/wpa_supplicant/wpa_supplicant-wlan0.conf
    echo ""                                       >> ${IMAGE_ROOTFS}/etc/wpa_supplicant/wpa_supplicant-wlan0.conf
    echo "network={"                              >> ${IMAGE_ROOTFS}/etc/wpa_supplicant/wpa_supplicant-wlan0.conf
    echo "        ssid=\"No Wi-Fi No Cry\""       >> ${IMAGE_ROOTFS}/etc/wpa_supplicant/wpa_supplicant-wlan0.conf
    echo "        psk=403dd4cfacfba96f860649c603a3bc9e61f6ca1a29573eb07bfb0893bfeb201b" >> ${IMAGE_ROOTFS}/etc/wpa_supplicant/wpa_supplicant-wlan0.conf
    echo "}"                                      >> ${IMAGE_ROOTFS}/etc/wpa_supplicant/wpa_supplicant-wlan0.conf
    echo ""                                       >> ${IMAGE_ROOTFS}/etc/wpa_supplicant/wpa_supplicant-wlan0.conf
    
    chmod 666 ${IMAGE_ROOTFS}/etc/wpa_supplicant/wpa_supplicant-wlan0.conf
}

wpa_supplicant_service_systemd() {
    cd ${IMAGE_ROOTFS}/etc/systemd/system/multi-user.target.wants/
    ln -s ../../../../lib/systemd/system/wpa_supplicant@.service. wpa_supplicant@wlan0.service
}

ROOTFS_POSTPROCESS_COMMAND += " \
    set_local_timezone ; \
    disable_bootlogd ; \
"

ROOTFS_POSTPROCESS_COMMAND_append_beaglebone-systemd = " \
    enable_dhcp_eth0 ; \
    enable_dhcp_wlan0 ; \
    wpa_supplicant_wlan0 ; \
    wpa_supplicant_service_systemd ; \
"

export IMAGE_BASENAME = "custom-image"
