SUMMARY = "A console development image with some C/C++ dev tools"
HOMEPAGE = "http://www.jumpnowtek.com"

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

IMAGE_INSTALL += " \
    ${CORE_OS} \
    ${EXTRA_TOOLS_INSTALL} \
    ${KERNEL_EXTRA_INSTALL} \
"

set_local_timezone() {
    ln -sf /usr/share/zoneinfo/EST5EDT ${IMAGE_ROOTFS}/etc/localtime
}

disable_bootlogd() {
    echo BOOTLOGD_ENABLE=no > ${IMAGE_ROOTFS}/etc/default/bootlogd
}

ROOTFS_POSTPROCESS_COMMAND += " \
    set_local_timezone ; \
    disable_bootlogd ; \
"

export IMAGE_BASENAME = "custom-image"

