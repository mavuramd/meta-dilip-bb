SUMMARY = "A Cutom development image"
HOMEPAGE = "https://github.com/mavuramd"

IMAGE_FEATURES += "package-management"
IMAGE_LINGUAS = "en-us"

inherit image

CORE_OS = " \
    openssh openssh-keygen openssh-sftp-server \
    packagegroup-core-boot \
    term-prompt \
    tzdata \
"

KERNEL_EXTRA_INSTALL = " \
    kernel-modules \
    load-modules \
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
    rndaddtoentcnt \
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

IMAGE_INSTALL += " \
    ${CORE_OS} \
    ${EXTRA_TOOLS_INSTALL} \
    ${KERNEL_EXTRA_INSTALL} \
    ${INSTALL_MISC} \
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

ROOTFS_POSTPROCESS_COMMAND += " \
    set_local_timezone ; \
    disable_bootlogd ; \
"

ROOTFS_POSTPROCESS_COMMAND_append_beaglebone-systemd = " \
    enable_dhcp_eth0 ; \
"

ROOTFS_POSTPROCESS_COMMAND_append_beaglebone-yocto = " \
    enable_dhcp_eth0 ; \
"

export IMAGE_BASENAME = "custom-image"
