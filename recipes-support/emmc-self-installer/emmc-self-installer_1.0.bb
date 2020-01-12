SUMMARY = "Scripts to support a BBB eMMC self installation"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://emmc_mk2parts.sh \
           file://emmc_copy_boot.sh \
           file://emmc_copy_rootfs.sh \
          "

PR = "r4"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 emmc_mk2parts.sh ${D}${bindir}
    install -m 0755 emmc_copy_boot.sh ${D}${bindir}
    install -m 0755 emmc_copy_rootfs.sh ${D}${bindir}
}

FILES_${PN} = "${sysconfdir} ${bindir}"

RDEPENDS_${PN} = "coreutils dosfstools e2fsprogs-mke2fs util-linux" 
