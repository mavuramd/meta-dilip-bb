#!/bin/sh
#
# Script to install a system onto the BBB eMMC
# This script handles the boot partition.
#
# This script should normally be run as
#
#  ./emmc_copy_boot.sh
#
# Assumes the following files are available in the local directory:
#
#   1) MLO-beaglebone
#   2) u-boot-beaglebone.img
#

MACHINE=beaglebone-systemd
DEV=/dev/mmcblk1p1
SRCDEV=/dev/mmcblk0p1
SRCDIR=/boot

echo "Mounting $DEV"
mount ${SRCDEV} ${SRCDIR}

if [ ! -b ${DEV} ]; then
    echo "Block device not found: ${DEV}"
    exit 1
fi

if [ ! -d /media ]; then
    echo "Mount point /media does not exist";
    exit 1
fi

echo "Formatting FAT partition on $DEV"
mkfs.vfat -F 32 ${DEV} -n BOOT

echo "Mounting $DEV"
mount ${DEV} /media

echo "Copying contents"
cp -r ${SRCDIR}/* /media/

echo "Unmounting ${DEV}"
umount ${DEV}

echo "Unmounting ${SRCDEV}"
umount ${SRCDEV}

echo "Done"
